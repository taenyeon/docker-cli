package com.example.dockercli.api.server.domain;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Server {
    // eureka에 등록된 서비스 이름과 동일해야함
    private String name;
    // gateway 라우팅 정보
    private String url;
    // 단일 혹은 다중 컨테이너 형태
    private ServerType serverType;

    private String managerId;
}
