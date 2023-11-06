package com.example.dockercli.api.dockerFile.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class DockerFileService {

    public boolean uploadDockerFile(String serverName, MultipartFile dockerFile) {
        boolean isSuccess = false;
        String path = "/Users/gimtaeyeon/fileTest/dockerfile/" + serverName;
        File dockerFileDir = new File(path);
        if (!dockerFileDir.exists()) {
            createNewDir(dockerFileDir);
        }
        String dockerFilePath = path + "/Dockerfile";
        try {
            dockerFile.transferTo(Paths.get(dockerFilePath));
            isSuccess = true;
        } catch (IOException e) {
            log.error("[DockerFileService.createNewDir Error] - e : {}", e);
        }
        return isSuccess;
    }

    private static void createNewDir(File dockerFileDir) {
        boolean mkdir = dockerFileDir.mkdir();
        if (mkdir) {
            log.info("[DockerFileService.createNewDir] - new Dir : path : {}", dockerFileDir.getPath());
        }
    }

}
