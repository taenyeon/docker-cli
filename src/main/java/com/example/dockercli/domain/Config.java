package com.example.dockercli.domain;

import java.util.List;

public class Config {
    String user;
    List<String> env; // "key=value"
    String image;
}
