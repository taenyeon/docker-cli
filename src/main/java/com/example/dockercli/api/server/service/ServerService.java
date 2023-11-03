package com.example.dockercli.api.server.service;

import com.example.dockercli.config.storage.server.domain.Server;
import com.example.dockercli.config.storage.server.storage.ServerStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServerService {
    private final ServerStorage storage;

    public ConcurrentMap<String, Server> getServersMap(){
        return storage.getServers();
    }

    public List<Server> getServersList() {
        return new ArrayList<>(getServersMap().values());
    }

    public boolean addServer(Server server){
        return storage.addServer(server);
    }
}
