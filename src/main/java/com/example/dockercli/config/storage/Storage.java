package com.example.dockercli.config.storage;


import com.example.dockercli.config.storage.container.domain.Container;
import com.example.dockercli.config.storage.dockerfile.domain.DockerFile;
import com.example.dockercli.config.storage.image.domain.Image;
import com.example.dockercli.config.storage.server.domain.Server;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Storage {

    // serverId <-> Server
    public static ConcurrentMap<String, Server> serverIdToServer = new ConcurrentHashMap<>();
    // containerId <-> Container
    public static ConcurrentMap<String, Container> containerIdToContainer = new ConcurrentHashMap<>();
    // imageId <-> Image
    public static ConcurrentMap<String, Image> imageIdToImage = new ConcurrentHashMap<>();

    // serverId <-> List<containerId>
    public static ConcurrentMap<String, List<String>> serverNameToContainerIds = new ConcurrentHashMap<>();
    // serverId <-> List<imageId>
    public static ConcurrentMap<String, List<String>> serverNameToImageIds = new ConcurrentHashMap<>();

    public static ConcurrentMap<String, DockerFile> serverNameToDockerFile = new ConcurrentHashMap<>();
}
