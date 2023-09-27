package com.example.dockercli.config.storage.image.domain;

import lombok.Data;

@Data
public class Image {

    private String id;
    private String repository;
    private String tag;
    private String size;
}
