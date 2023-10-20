package com.example.dockercli.config.storage.server.storage;

import com.example.dockercli.config.storage.server.domain.Server;
import com.example.dockercli.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServerStorage {

    private final ObjectMapper objectMapper;

    public ConcurrentMap<String, Server> getServers() {

        ConcurrentMap<String, Server> serverMap = FileUtil.getMultiValueMappingConcurrentMap("/Users/gimtaeyeon/fileTest/servers.json", Server.class);
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
