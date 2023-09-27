package com.example.dockercli.config.storage.container.service;

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
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContainerService {

    private final ObjectMapper objectMapperPascal;
    private final ObjectMapper objectMapper;

    public Map<String, Container> getContainers() {
        Map<String, Container> containerInfo = getContainerInfo();
        Map<String, Stat> containerStatInfo = getContainerStatInfo();
        containerInfo.forEach((key, container) -> {
            String substring = key.substring(0, 12);
            Stat stat = containerStatInfo.get(substring);
            container.setStat(stat);
        });
        return containerInfo;
    }

    public Map<String, Container> getContainerInfo() {
        String command = "docker inspect $(docker ps -aq)";
        Process exec = null;
        String readLine;
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Container> containerMap = new HashMap<>();
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
            containerMap = containers.stream().collect(Collectors.toMap(Container::getId, container -> container));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return containerMap;
    }

    public Map<String, Stat> getContainerStatInfo() {
        String command = "docker stats --no-stream --no-trunc --format \\\n" +
                "    \"{\\\"name\\\":\\\"{{ .Name }}\\\",\\\"id\\\":\\\"{{ .Container }}\\\",\\\"memory\\\":{\\\"raw\\\":\\\"{{ .MemUsage }}\\\",\\\"percent\\\":\\\"{{ .MemPerc }}\\\"},\\\"cpuPer\\\":\\\"{{ .CPUPerc }}\\\"}\"";
        Process exec = null;
        Map<String, Stat> containerMap = new HashMap<>();
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
