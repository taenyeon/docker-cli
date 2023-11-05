package com.example.dockercli.api.server.domain;

import com.example.dockercli.config.storage.container.domain.Container;
import com.example.dockercli.config.storage.image.domain.Image;
import com.example.dockercli.config.storage.server.domain.Server;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerDto {

    private Server server;
    private List<Image> images;

    private List<Container> containers;

}
