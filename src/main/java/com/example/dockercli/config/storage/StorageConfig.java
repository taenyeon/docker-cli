package com.example.dockercli.config.storage;

import com.example.dockercli.config.storage.container.domain.Container;
import com.example.dockercli.config.storage.container.service.ContainerService;
import com.example.dockercli.config.storage.image.domain.Image;
import com.example.dockercli.config.storage.image.service.ImageService;
import com.example.dockercli.config.storage.server.domain.Server;
import com.example.dockercli.config.storage.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class StorageConfig {

    private final ServerService serverService;
    private final ContainerService containerService;
    private final ImageService imageService;

    public void update() {
        Map<String, Server> servers = serverService.getServers();
        Map<String, Container> containers = containerService.getContainers();
        Map<String, Image> images = imageService.getImages();

        Map<String, List<String>> serverNameToContainerIds = new ConcurrentHashMap<>();
        Map<String, List<String>> serverNameToImageIds = new ConcurrentHashMap<>();

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
