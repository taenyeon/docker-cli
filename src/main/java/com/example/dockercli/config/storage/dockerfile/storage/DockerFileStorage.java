package com.example.dockercli.config.storage.dockerfile.storage;

import com.example.dockercli.config.storage.dockerfile.domain.DockerFile;
import com.example.dockercli.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class DockerFileStorage {

    public ConcurrentMap<String, DockerFile> getDockerFiles() {
        ConcurrentHashMap<String, DockerFile> serverNameToDockerFile = new ConcurrentHashMap<>();
        String path = "/Users/gimtaeyeon/fileTest/dockerfile";
        File dockerFileRootDir = new File(path);
        File[] dockerFileDirs = dockerFileRootDir.listFiles();
        Arrays.stream(dockerFileDirs).forEach(
                dockerFileDir -> {
                    String serverName = dockerFileDir.getName();
                    String dockerFilePath = path + "/" + serverName + "/Dockerfile";
                    String payload = FileUtil.getStringValue(dockerFilePath);
                    DockerFile dockerFile = DockerFile.builder()
                            .serverName(serverName)
                            .path(dockerFilePath)
                            .payload(payload)
                            .build();
                    serverNameToDockerFile.put(serverName, dockerFile);
                }
        );
        return serverNameToDockerFile;
    }
}
