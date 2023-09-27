package com.example.dockercli.config.storage.container.domain;

import lombok.Data;

import java.util.List;

@Data
public class Config {
    String user;
    List<String> env; // "key=value"
    String image;
}
