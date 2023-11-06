package com.example.dockercli.config.storage.container.storage;

import com.example.dockercli.api.server.domain.ServerDto;
import com.example.dockercli.config.storage.container.domain.Container;
import com.example.dockercli.config.storage.container.domain.Stat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

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
                stringBuilder.append(readLine);
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
        if (StringUtils.isEmpty(result)) {
            return new ConcurrentHashMap<>();
        }
        try {
            List<Container> containers = objectMapperPascal.readValue(result, new TypeReference<List<Container>>() {
            });
            containers.forEach(container -> {
                String containerPrefix = container.getName().split("-")[0];
                containerPrefix = containerPrefix.replace("/", "");
                container.setName(containerPrefix);
            });
            containerMap = containers.stream()
                    .collect(Collectors.toConcurrentMap(Container::getId, container -> container));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return containerMap;
    }

    public boolean stopContainer(String containerId) {
        String command = "docker stop " + containerId;
        return sendCommand(containerId, command);
    }

    public boolean startContainer(String containerId) {
        String command = "docker start " + containerId;
        return sendCommand(containerId, command);
    }

    public boolean dropContainer(String containerId) {
        boolean isStopped = stopContainer(containerId);
        if (isStopped) {
            String command = "docker rm " + containerId;
            return sendCommand(containerId, command);
        } else {
            return false;
        }
    }

    public boolean createContainer(ServerDto serverDto, String imageId){
        String serverName = serverDto.getServer().getName();
        String command = "docker run --name " + serverName + " " + imageId + " /bin/bash";
        log.info("command : {}", command);
        return sendCommand(null, command);
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

    private static boolean sendCommand(String containerId, String command) {
        Process exec = null;
        String readLine;
        StringBuilder result = new StringBuilder();
        boolean isSuccess = false;
        try {
            String[] cmd = {"/bin/zsh", "-c", command};
            exec = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            while ((readLine = bufferedReader.readLine()) != null) {
                result.append(readLine);
            }
            exec.waitFor();
        } catch (Exception e) {
            log.error("error - {}", e.getMessage());
        } finally {
            if (exec != null) {
                exec.destroy();
            }
        }
        log.info("result : {}", result);
        if (containerId != null && result.toString().equals(containerId)) {
            isSuccess = true;
        }
        return isSuccess;
    }
}
