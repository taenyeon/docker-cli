package com.example.dockercli;

import org.junit.jupiter.api.Test;

public class ShellServiceTest {

    @Test
    public void shellTest(){
        ShellService shellService = new ShellService();
        String containerInfo = shellService.getContainerInfo();
        System.out.println("containerInfo : " + containerInfo);
    }
}
