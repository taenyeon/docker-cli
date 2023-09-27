package com.example.dockercli.config.storage.server.service;

import com.example.dockercli.config.storage.server.domain.Server;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServerService {

    private final ObjectMapper objectMapper;

    public Map<String, Server> getServers() {
        Map<String, Server> serverMap = new ConcurrentHashMap<>();
        try {
            FileReader fileReader = new FileReader("/Users/gimtaeyeon/fileTest/servers.json");
            BufferedReader reader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String result = stringBuilder.toString();
            serverMap = objectMapper.readValue(result, new TypeReference<ConcurrentHashMap<String, Server>>() {
            });

        } catch (IOException e) {
            log.debug("Read File Error - error : ", e);
        }
        return serverMap;
    }

    public boolean writeServers(Map<String, Server> servers) {
        boolean isSuccess = false;
        try {
            File file = new File("/Users/gimtaeyeon/fileTest/servers.json");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            String serversString = objectMapper.writeValueAsString(servers);
            bufferedWriter.write(serversString);
            isSuccess = true;
            bufferedWriter.close();

        } catch (IOException e) {
            log.debug("Write File Error - error : ", e);
        }
        return isSuccess;
    }

    public boolean addServer(Server server) {
        Map<String, Server> servers = getServers();
        if (servers.containsKey(server.getName())) {
            throw new IllegalStateException("is exist server");
        }
        servers.put(server.getName(), server);
        return writeServers(servers);
    }

}
