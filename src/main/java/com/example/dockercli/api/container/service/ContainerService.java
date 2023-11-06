package com.example.dockercli.api.container.service;

import com.example.dockercli.api.container.domain.ContainerCreateForm;
import com.example.dockercli.api.server.domain.ServerDto;
import com.example.dockercli.api.server.service.ServerService;
import com.example.dockercli.config.storage.Storage;
import com.example.dockercli.config.storage.StorageConfig;
import com.example.dockercli.config.storage.container.domain.Container;
import com.example.dockercli.config.storage.container.storage.ContainerStorage;
import com.example.dockercli.config.storage.server.domain.ServerType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContainerService {

    private final StorageConfig storageConfig;
    private final ServerService serverService;
    private final ContainerStorage containerStorage;

    public Container getContainer(String containerId) {
        return Storage.containerIdToContainer.get(containerId);
    }

    public boolean stopContainer(String containerId) {
        boolean isSuccess = containerStorage.stopContainer(containerId);
        storageConfig.update();
        return isSuccess;
    }

    public boolean startContainer(String containerId) {
        boolean isSuccess = containerStorage.startContainer(containerId);
        storageConfig.update();
        return isSuccess;
    }

    public boolean dropContainer(String containerId) {
        boolean isSuccess = containerStorage.dropContainer(containerId);
        storageConfig.update();
        return isSuccess;
    }

    public boolean createContainer(ContainerCreateForm containerCreateForm) {
        ServerDto server = serverService.getServer(containerCreateForm.getServerName());
        if (server.getServer().getServerType() == ServerType.SINGLE && (!server.getContainers().isEmpty())) {
            throw new IllegalStateException("This Server Is SINGLE Type - Not Allow Multi Container");
        }
        log.info("[ContainerService.createContainer] - param : {}", containerCreateForm);
        return containerStorage.createContainer(server, containerCreateForm.getImageId());
    }

}
