package com.example.dockercli.config.storage.server.service;

import com.example.dockercli.config.storage.server.domain.Server;
import com.example.dockercli.util.FileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServerService {

    private final ObjectMapper objectMapper;

    public Map<String, Server> getServers() {

        Map<String, Server> serverMap = FileUtil.getByAllLine("/Users/gimtaeyeon/fileTest/servers.json", new ConcurrentHashMap<String, Server>());
        if (serverMap == null) {
            serverMap = new ConcurrentHashMap<>();
        }
        return serverMap;
    }

    public boolean writeServers(Map<String, Server> servers) {
        return FileUtil.writeFile("/Users/gimtaeyeon/fileTest/servers.json", servers);
    }

    public boolean addServer(Server server) {
        Map<String, Server> servers = getServers();
        if (servers.containsKey(server.getName())) {
            throw new IllegalStateException("is exist server");
        }
        servers.put(server.getName(), server);
        return writeServers(servers);
    }

}
