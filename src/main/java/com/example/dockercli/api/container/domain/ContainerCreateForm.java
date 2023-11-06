package com.example.dockercli.api.container.domain;


import lombok.Data;

@Data
public class ContainerCreateForm {
    private String imageId;
    private String serverName;

}
