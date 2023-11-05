package com.example.dockercli.api.container.controller;

import com.example.dockercli.api.container.service.ContainerService;
import com.example.dockercli.config.storage.container.domain.Container;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/container")
@Slf4j
@RequiredArgsConstructor
public class ContainerController {

    private final ContainerService containerService;

    @GetMapping("/{containerId}")
    public ResponseEntity<Container> getContainer(@PathVariable String containerId){
        return ResponseEntity.ok(containerService.getContainer(containerId));
    }
}
