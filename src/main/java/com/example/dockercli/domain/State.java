package com.example.dockercli.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class State {
    boolean running;
    int pid;
    LocalDateTime startedAt;
    LocalDateTime finishedAt;
}
