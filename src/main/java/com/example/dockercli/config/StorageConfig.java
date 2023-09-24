package com.example.dockercli.config;


import com.example.dockercli.api.container.domain.Container;
import com.example.dockercli.api.image.domain.Image;
import com.example.dockercli.api.server.domain.Server;
import lombok.Data;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class StorageConfig {

    private StorageConfig() {
        throw new IllegalStateException("Static Access Class");
    }

    // serverId <-> Server
    private static Map<String, Server> serverIdToServer = new ConcurrentHashMap<>();
    // containerId <-> Container
    private static Map<String, Container> containerIdToContainer = new ConcurrentHashMap<>();
    // imageId <-> Image
    private static Map<String, Image> imageIdToImage = new ConcurrentHashMap<>();

    // serverId <-> List<containerId>
    private static Map<String, List<String>> serverIdToContainerIds = new ConcurrentHashMap<>();
    // serverId <-> List<imageId>
    private static Map<String, List<String>> serverIdToImageIds = new ConcurrentHashMap<>();
    // containerId <-> imageId
    private static Map<String, List<String>> containerIdToImageId = new ConcurrentHashMap<>();

}
