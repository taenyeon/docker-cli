package com.example.dockercli.api.container.service;

import com.example.dockercli.config.storage.Storage;
import com.example.dockercli.config.storage.StorageConfig;
import com.example.dockercli.config.storage.container.domain.Container;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContainerService {

    private final StorageConfig storageConfig;

    public Container getContainer(String containerId){
        return Storage.containerIdToContainer.get(containerId);
    }

}
