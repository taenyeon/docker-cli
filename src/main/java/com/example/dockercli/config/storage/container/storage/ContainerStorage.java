package com.example.dockercli.config.storage.container.storage;

import com.example.dockercli.config.storage.container.domain.Container;
import com.example.dockercli.config.storage.container.domain.Stat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContainerStorage {

    private final ObjectMapper objectMapperPascal;
    private final ObjectMapper objectMapper;

    public ConcurrentMap<String, Container> getContainers() {
        ConcurrentMap<String, Container> containerInfo = getContainerInfo();
        ConcurrentMap<String, Stat> containerStatInfo = getContainerStatInfo();
        containerInfo.forEach((key, container) -> {
            String substring = key.substring(0, 12);
            Stat stat = containerStatInfo.get(substring);
            container.setStat(stat);
        });
        return containerInfo;
    }

    public ConcurrentMap<String, Container> getContainerInfo() {
        String command = "docker inspect $(docker ps -aq)";
        Process exec = null;
        String readLine;
        StringBuilder stringBuilder = new StringBuilder();
        ConcurrentMap<String, Container> containerMap = new ConcurrentHashMap<>();
        try {
            String[] cmd = {"/bin/zsh", "-c", command};
            exec = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            while ((readLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(new String(readLine));
            }
            exec.waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (exec != null) {
                exec.destroy();
            }
        }
        String result = stringBuilder.toString();
        try {
            List<Container> containers = objectMapperPascal.readValue(result, new TypeReference<List<Container>>() {
            });
            containerMap = containers.stream().collect(Collectors.toConcurrentMap(Container::getId, container -> container));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return containerMap;
    }

    public ConcurrentMap<String, Stat> getContainerStatInfo() {
        String command = "docker stats --no-stream --no-trunc --format \\\n" +
                "    \"{\\\"name\\\":\\\"{{ .Name }}\\\",\\\"id\\\":\\\"{{ .Container }}\\\",\\\"memory\\\":{\\\"raw\\\":\\\"{{ .MemUsage }}\\\",\\\"percent\\\":\\\"{{ .MemPerc }}\\\"},\\\"cpuPer\\\":\\\"{{ .CPUPerc }}\\\"}\"";
        Process exec = null;
        ConcurrentMap<String, Stat> containerMap = new ConcurrentHashMap<>();
        String result;
        try {
            String[] cmd = {"/bin/zsh", "-c", command};
            exec = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            while ((result = bufferedReader.readLine()) != null) {
                Stat container = objectMapper.readValue(result, Stat.class);
                containerMap.put(container.getId(), container);
            }
            exec.waitFor();
        } catch (Exception e) {
            log.error("error - {}", e.getMessage());
        } finally {
            if (exec != null) {
                exec.destroy();
            }
        }
        return containerMap;
    }
}
