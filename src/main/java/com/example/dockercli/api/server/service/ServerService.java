package com.example.dockercli.api.server.service;

import com.example.dockercli.api.server.domain.ServerDto;
import com.example.dockercli.config.storage.Storage;
import com.example.dockercli.config.storage.StorageConfig;
import com.example.dockercli.config.storage.container.domain.Container;
import com.example.dockercli.config.storage.container.storage.ContainerStorage;
import com.example.dockercli.config.storage.image.domain.Image;
import com.example.dockercli.config.storage.image.storage.ImageStorage;
import com.example.dockercli.config.storage.server.domain.Server;
import com.example.dockercli.config.storage.server.storage.ServerStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import static com.example.dockercli.config.storage.Storage.serverIdToServer;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServerService {
    private final ServerStorage serverStorage;
    private final StorageConfig storageConfig;

    public ConcurrentMap<String, Server> getServersMap() {
        return Storage.serverIdToServer;
    }

    public List<Server> getServersList() {
        return new ArrayList<>(getServersMap().values());
    }

    public ServerDto getServer(String serverName) {

        Server server = getServersMap().get(serverName);

        List<Container> containers = new ArrayList<>();
        Storage.serverNameToContainerIds.get(serverName)
                .forEach(containerId -> containers.add(Storage.containerIdToContainer.get(containerId)));

        List<Image> images = new ArrayList<>();
        Storage.serverNameToImageIds.get(serverName)
                .forEach(imageId -> images.add(Storage.imageIdToImage.get(imageId)));

        return ServerDto.builder()
                .server(server)
                .containers(containers)
                .images(images)
                .build();
    }

    public boolean addServer(Server server) {
        boolean isSuccess = serverStorage.addServer(server);
        storageConfig.update();
        return isSuccess;
    }

    public boolean modifyServer(Server server){
        return serverStorage.modifyServer(server);
    }

    public boolean deleteServer(String serverName){
        return serverStorage.deleteServer(serverName);
    }
}
