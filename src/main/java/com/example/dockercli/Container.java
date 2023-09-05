package com.example.dockercli;

import lombok.Data;

@Data
public class Container {

    private String name;
    private String id;
    private Memory memory;
    private Double cpu;

    public void setCpu(String cpu) {
        this.cpu = Double.valueOf(cpu.replace("%", ""));
    }
}
