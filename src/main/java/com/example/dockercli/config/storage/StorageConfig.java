package com.example.dockercli.config.storage;

import com.example.dockercli.config.storage.container.domain.Container;
import com.example.dockercli.config.storage.container.storage.ContainerStorage;
import com.example.dockercli.config.storage.dockerfile.domain.DockerFile;
import com.example.dockercli.config.storage.dockerfile.storage.DockerFileStorage;
import com.example.dockercli.config.storage.image.domain.Image;
import com.example.dockercli.config.storage.image.storage.ImageStorage;
import com.example.dockercli.config.storage.server.domain.Server;
import com.example.dockercli.config.storage.server.storage.ServerStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class StorageConfig {

    private final ServerStorage serverService;
    private final ContainerStorage containerService;
    private final ImageStorage imageService;
    private final DockerFileStorage dockerFileService;

    @PostConstruct
    public void init(){
        this.update();
    }

    public void update() {
        log.info("[StorageConfig.update] Storage Update Start - startAt : {}", LocalDateTime.now());
        ConcurrentMap<String, Server> servers = serverService.getServers();
        ConcurrentMap<String, Container> containers = containerService.getContainers();
        ConcurrentMap<String, Image> images = imageService.getImages();

        ConcurrentMap<String, DockerFile> serverNameToDockerFile = dockerFileService.getDockerFiles();
        ConcurrentMap<String, List<String>> serverNameToContainerIds = new ConcurrentHashMap<>();
        ConcurrentMap<String, List<String>> serverNameToImageIds = new ConcurrentHashMap<>();

        servers.forEach((serverName, server) -> {
            List<String> containerIds = new ArrayList<>();
            List<String> imageIds = new ArrayList<>();

            containers.forEach((containerId, container) -> {
                if (StringUtils.equalsIgnoreCase(container.getName(), serverName)) {
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
        log.info("[StorageConfig.update] Storage Update - serverIdToServer : {}", Storage.serverIdToServer);

        Storage.containerIdToContainer = containers;
        log.info("[StorageConfig.update] Storage Update - containerIdToContainer : {}", Storage.containerIdToContainer);

        Storage.imageIdToImage = images;
        log.info("[StorageConfig.update] Storage Update - imageIdToImage : {}", Storage.imageIdToImage);

        Storage.serverNameToContainerIds = serverNameToContainerIds;
        log.info("[StorageConfig.update] Storage Update - serverNameToContainerIds : {}", Storage.serverNameToContainerIds);

        Storage.serverNameToImageIds = serverNameToImageIds;
        log.info("[StorageConfig.update] Storage Update - serverNameToImageIds : {}", Storage.serverNameToImageIds);

        Storage.serverNameToDockerFile = serverNameToDockerFile;
        log.info("[StorageConfig.update] Storage Update - serverNameToDockerFile : {}", Storage.serverNameToDockerFile);

        log.info("[StorageConfig.update] Storage Update End - endAt : {}", LocalDateTime.now());
    }

}
