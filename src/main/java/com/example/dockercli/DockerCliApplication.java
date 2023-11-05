package com.example.dockercli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DockerCliApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerCliApplication.class, args);
    }

}
