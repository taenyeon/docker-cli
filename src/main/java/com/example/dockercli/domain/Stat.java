package com.example.dockercli.domain;

import lombok.Data;

@Data
public class Stat {

    private String name;
    private String id;
    private Memory memory;
    private Double cpuPer;

    public void setCpuPer(String cpuPer) {
        this.cpuPer = Double.valueOf(cpuPer.replace("%", ""));
    }
}
