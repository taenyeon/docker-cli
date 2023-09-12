package com.example.dockercli.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class NetworkSettings {
    Map<String, List<Port>> ports;
    String gateway;
    String ipAddress;

    @Data
    static class Port {
        String hostIp;
        String hostPort;
    }
}
