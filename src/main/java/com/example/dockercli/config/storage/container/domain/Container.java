package com.example.dockercli.config.storage.container.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Container {
    String id;
    String name;
    LocalDateTime created;
    String platform;
    Stat stat;
    State state;
    Config config;
    NetworkSettings networkSettings;


}
