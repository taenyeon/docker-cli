package com.example.dockercli.domain.service;

import com.example.dockercli.domain.container.Container;
import lombok.Data;

import java.util.List;

@Data
public class Service {
    // eureka에 등록된 서비스 이름과 동일해야함
    private String name;
    // gateway 라우팅 정보
    private String url;
    // 단일 혹은 다중 컨테이너 형태
    private ServiceType serviceType;
    // 컨테이너 목록
    private List<Container> containers;

    private String managerId;

    private List<String> images;
}
