package com.example.dockercli.api.container.domain;

import lombok.Data;

@Data
public class Memory {
    private String raw;
    private Double percent;

    public void setPercent(String percent){
        this.percent = Double.valueOf(percent.replace("%",""));
    }

}
