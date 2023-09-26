package com.example.dockercli.api.server.service;

import com.example.dockercli.api.server.domain.Server;
import com.example.dockercli.api.server.domain.ServerType;
import com.example.dockercli.config.ApplicationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

class ServerServiceTest {

    Logger log = LoggerFactory.getLogger(ServerService.class);
    ServerService serverService;
    Server server;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ApplicationConfig().objectMapper();
        serverService = new ServerService(objectMapper);
        server = Server.builder()
                .name("test")
                .url("test///")
                .serverType(ServerType.SINGLE)
                .managerId("test")
                .build();
    }

    @Test
    void getServers() {
        Map<String, Server> servers = serverService.getServers();
        log.info("servers : {}", servers);
    }

    @Test
    void addServer() {
        boolean isSuccess = serverService.addServer(server);
        log.info("isSuccess : {}", isSuccess);
        Map<String, Server> servers = serverService.getServers();
        log.info("servers : {}", servers);
    }
}