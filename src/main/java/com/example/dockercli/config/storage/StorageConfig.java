package com.example.dockercli.config.storage;

import com.example.dockercli.config.storage.container.domain.Container;
import com.example.dockercli.config.storage.container.storage.ContainerStorage;
import com.example.dockercli.config.storage.image.domain.Image;
import com.example.dockercli.config.storage.image.storage.ImageStorage;
import com.example.dockercli.config.storage.server.domain.Server;
import com.example.dockercli.config.storage.server.storage.ServerStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class StorageConfig {

    private final ServerStorage serverService;
    private final ContainerStorage containerService;
    private final ImageStorage imageService;

    @PostConstruct
    public void init(){
        this.update();
    }

    public void update() {
        ConcurrentMap<String, Server> servers = serverService.getServers();
        ConcurrentMap<String, Container> containers = containerService.getContainers();
        ConcurrentMap<String, Image> images = imageService.getImages();

        ConcurrentMap<String, List<String>> serverNameToContainerIds = new ConcurrentHashMap<>();
        ConcurrentMap<String, List<String>> serverNameToImageIds = new ConcurrentHashMap<>();

        servers.forEach((serverName, server) -> {
            List<String> containerIds = new ArrayList<>();
            List<String> imageIds = new ArrayList<>();

            containers.forEach((containerId, container) -> {
                String containerPrefix = container.getName().split("-")[0];
                if (StringUtils.equalsIgnoreCase(containerPrefix, serverName)) {
                    containerIds.add(containerId);
                }
            });

            serverNameToContainerIds.put(serverName, containerIds);

            images.forEach((imageId, image) -> {
                String imagePrefix = image.getRepository().split("-")[0];
                if (StringUtils.equalsIgnoreCase(imagePrefix, serverName)) {
                    imageIds.add(imageId);
                }
            });
            serverNameToImageIds.put(serverName,imageIds);
        });

        Storage.serverIdToServer = servers;
        Storage.containerIdToContainer = containers;
        Storage.imageIdToImage = images;
        Storage.serverNameToContainerIds = serverNameToContainerIds;
        Storage.serverNameToImageIds = serverNameToImageIds;
    }

}
