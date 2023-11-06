package com.example.dockercli.api.container.controller;

import com.example.dockercli.api.container.domain.ContainerCreateForm;
import com.example.dockercli.api.container.service.ContainerService;
import com.example.dockercli.config.storage.container.domain.Container;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{containerId}/stop")
    public ResponseEntity<Boolean> stopContainer(@PathVariable String containerId){
        return ResponseEntity.ok(containerService.stopContainer(containerId));
    }
    @GetMapping("/{containerId}/start")
    public ResponseEntity<Boolean> startContainer(@PathVariable String containerId){
        return ResponseEntity.ok(containerService.startContainer(containerId));
    }

    @DeleteMapping("/{containerId}")
    public ResponseEntity<Boolean> dropContainer(@PathVariable String containerId){
        return ResponseEntity.ok(containerService.dropContainer(containerId));
    }

    @PostMapping("")
    public ResponseEntity<Boolean> createContainer(@RequestBody ContainerCreateForm containerCreateForm){
        return ResponseEntity.ok(containerService.createContainer(containerCreateForm));
    }
}
