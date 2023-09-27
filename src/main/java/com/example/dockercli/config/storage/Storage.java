package com.example.dockercli.config.storage;


import com.example.dockercli.config.storage.container.domain.Container;
import com.example.dockercli.config.storage.image.domain.Image;
import com.example.dockercli.config.storage.server.domain.Server;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {


    // serverId <-> Server
    public static Map<String, Server> serverIdToServer = new ConcurrentHashMap<>();
    // containerId <-> Container
    public static Map<String, Container> containerIdToContainer = new ConcurrentHashMap<>();
    // imageId <-> Image
    public static Map<String, Image> imageIdToImage = new ConcurrentHashMap<>();

    // serverId <-> List<containerId>
    public static Map<String, List<String>> serverNameToContainerIds = new ConcurrentHashMap<>();
    // serverId <-> List<imageId>
    public static Map<String, List<String>> serverNameToImageIds = new ConcurrentHashMap<>();

}
