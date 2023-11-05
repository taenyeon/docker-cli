package com.example.dockercli.api.server.controller;


import com.example.dockercli.api.server.domain.ServerDto;
import com.example.dockercli.api.server.service.ServerService;
import com.example.dockercli.config.security.domain.User;
import com.example.dockercli.config.storage.server.domain.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/server")
@Slf4j
@RequiredArgsConstructor
public class ServerController {

    private final ServerService serverService;

    @GetMapping("")
    public ResponseEntity<List<Server>> getServers() {
        return ResponseEntity.ok(serverService.getServersList());
    }

    @PostMapping("")
    public ResponseEntity<Object> addServer(@RequestBody Server server) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        server.setManagerId(username);
        return ResponseEntity.ok(serverService.addServer(server));
    }

    @GetMapping("/{serverName}")
    public ResponseEntity<ServerDto> getServer(@PathVariable String serverName) {
        return ResponseEntity.ok(serverService.getServer(serverName));
    }

    @PutMapping("/{serverName}")
    public ResponseEntity<Object> modifyServer(@RequestBody Server server, @PathVariable String serverName) {
        server.setName(serverName);
        return ResponseEntity.ok(serverService.modifyServer(server));
    }

    @DeleteMapping("/{serverName}")
    public ResponseEntity<Object> deleteServer(@PathVariable String serverName) {
        return ResponseEntity.ok(serverService.deleteServer(serverName));
    }


}
