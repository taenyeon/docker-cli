package com.example.dockercli.api.server.service;

import com.example.dockercli.api.container.domain.Container;
import com.example.dockercli.api.server.domain.Server;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServerService {

    private final ObjectMapper objectMapper;

    public Map<Integer, Server> getServerList() {
        Map<Integer, Server> serverMap = null;
        try {
            FileReader fileReader = new FileReader("/Users/gimtaeyeon/fileTest/serverList");
            BufferedReader reader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String result = stringBuilder.toString();
            serverMap = objectMapper.readValue(result, new TypeReference<Map<Integer, Server>>() {
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return serverMap;
    }

//    public boolean addServer(){
//
//    }

}
