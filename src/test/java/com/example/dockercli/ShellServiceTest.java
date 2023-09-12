package com.example.dockercli;

import com.example.dockercli.config.ApplicationConfig;
import com.example.dockercli.domain.Container;
import com.example.dockercli.domain.Stat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ShellServiceTest {

    ShellService shellService;

    @BeforeEach
    void setup(){
    ApplicationConfig applicationConfig = new ApplicationConfig();
    shellService = new ShellService(applicationConfig.objectMapperPascal(),applicationConfig.objectMapper());

    }
    @Test
    public void shellTest(){
        Map<String, Stat> containerInfo = shellService.getContainerStatInfo();
        System.out.println("containerInfo : " + containerInfo);
    }

    @Test
    public void getContainer(){
        Map<String, Container> containerInfo = shellService.getContainerInfo();
        System.out.println("containerInfo : " + containerInfo);
    }

    @Test
    public void getContainerMap() throws JsonProcessingException {
        Map<String, Container> containerInfo = shellService.getContainerMap();
        ObjectMapper objectMapper = new ApplicationConfig().objectMapper();
        String value = objectMapper.writeValueAsString(containerInfo);
        System.out.println("containerInfo : " + value);
    }
}
