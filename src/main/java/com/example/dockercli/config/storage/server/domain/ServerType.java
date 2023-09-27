package com.example.dockercli.config.storage.server.domain;

import lombok.Getter;

@Getter
public enum ServerType {
    // 단일 컨테이너
    SINGLE("SINGLE"),
    // 다중 컨테이너
    MULTIPLE("MULTIPLE");

    private final String code;

    ServerType(String name) {
        this.code = name;
    }
}
