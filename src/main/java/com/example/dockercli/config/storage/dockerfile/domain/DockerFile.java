package com.example.dockercli.config.storage.dockerfile.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DockerFile {

    private String serverName;

    private String path;

    private String payload;
}
