package com.example.dockercli.config.storage.container.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class Container {
    String id;
    String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'")
    LocalDateTime created;
    String platform;
    Stat stat;
    State state;
    Config config;
    NetworkSettings networkSettings;
}
