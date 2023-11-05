package com.example.dockercli.config.storage.server.storage;

import com.example.dockercli.config.storage.Storage;
import com.example.dockercli.config.storage.server.domain.Server;
import com.example.dockercli.util.FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.example.dockercli.config.storage.Storage.serverIdToServer;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServerStorage {

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
        ConcurrentMap<String, Server> servers = Storage.serverIdToServer;
        if (servers.containsKey(server.getName())) {
            throw new IllegalStateException("is exist server");
        }
        servers.put(server.getName(), server);
        return writeServers(servers);
    }
    
    public boolean modifyServer(Server server){
        ConcurrentMap<String, Server> servers = Storage.serverIdToServer;
        if (!servers.containsKey(server.getName())) {
            throw new IllegalStateException("is not exist server");
        }
        String managerId = servers.get(server.getName()).getManagerId();
        server.setManagerId(managerId);
        servers.replace(server.getName(), server);
        return writeServers(servers);
    }

    public boolean deleteServer(String serverName){
        ConcurrentMap<String, Server> servers = Storage.serverIdToServer;
        if (!servers.containsKey(serverName)) {
            throw new IllegalStateException("is not exist server");
        }
        servers.remove(serverName);
        return writeServers(servers);
    }

}
