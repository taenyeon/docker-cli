package com.example.dockercli.config.storage;

import com.example.dockercli.config.storage.container.domain.Container;
import com.example.dockercli.config.storage.image.domain.Image;
import com.example.dockercli.config.storage.server.domain.Server;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class StorageConfigTest {

    Logger log = LoggerFactory.getLogger(StorageConfig.class);

    @Autowired
    private StorageConfig storageConfig;

    @Test
    @DisplayName("Storage 정보 조회")
    void get() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            log.info("get#######################");
            Map<String, Server> servers = Storage.serverIdToServer;
            log.info("servers : {}", servers);
            Map<String, Image> images = Storage.imageIdToImage;
            log.info("images : {}", images);
            Map<String, Container> containers = Storage.containerIdToContainer;
            log.info("containers : {}", containers);
            Thread.sleep(1000);
        }
    }

    @Test
    @DisplayName("Storage 정보 업데이트")
    void update() throws InterruptedException {
        Thread.sleep(3000);
        storageConfig.update();
        log.info("update#####################");
        Map<String, Server> servers = Storage.serverIdToServer;
        log.info("servers : {}", servers);
        Map<String, Image> images = Storage.imageIdToImage;
        log.info("images : {}", images);
        Map<String, Container> containers = Storage.containerIdToContainer;
        log.info("containers : {}", containers);
    }

}