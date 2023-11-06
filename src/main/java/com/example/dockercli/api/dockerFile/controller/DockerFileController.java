package com.example.dockercli.api.dockerFile.controller;

import com.example.dockercli.api.dockerFile.service.DockerFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Locale;

@RestController
@RequestMapping("/dockerfile")
@Slf4j
@RequiredArgsConstructor
public class DockerFileController {

    private final DockerFileService dockerFileService;

    @PostMapping("/{serverName}")
    public ResponseEntity<Object> addDockerFile(@PathVariable String serverName,
                                                MultipartHttpServletRequest request) {
        MultipartFile dockerFile = request.getFile("dockerFile");
        log.info("file : {}", dockerFile.getOriginalFilename().toLowerCase(Locale.ROOT));
        if (dockerFile != null && !dockerFile.getOriginalFilename().toLowerCase(Locale.ROOT).equals("dockerfile")) {
            throw new IllegalStateException("not allow file");
        }
        log.info("[DockerFileController.addDockerFile] param - serverName : {}, dockerFile : {}", serverName, dockerFile);
        return ResponseEntity.ok(dockerFileService.uploadDockerFile(serverName, dockerFile));
    }
}
