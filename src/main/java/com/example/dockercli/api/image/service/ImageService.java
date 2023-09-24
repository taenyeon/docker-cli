package com.example.dockercli.api.image.service;

import com.example.dockercli.api.image.domain.Image;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    private final ObjectMapper objectMapper;

    public Map<String, Image> getImages() {
        String command = "docker images --format \"{\\\"id\\\": \\\"{{ .ID }}\\\", \\\"repository\\\": \\\"{{ .Repository }}\\\", \\\"tag\\\": \\\"{{ .Tag }}\\\", \\\"size\\\": \\\"{{ .VirtualSize }}\\\" }\"";
        Process exec = null;
        String result;
        Map<String, Image> imageMap = new HashMap<>();
        try {
            String[] cmd = {"/bin/zsh", "-c", command};
            exec = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            while ((result = bufferedReader.readLine()) != null) {
                Image image = objectMapper.readValue(result, Image.class);
                if (image != null) {
                    imageMap.put(image.getId(), image);
                }
            }
            exec.waitFor();
        } catch (Exception e) {
            log.error("error - {}", e.getMessage());
        } finally {
            if (exec != null) {
                exec.destroy();
            }
        }
        return null;
    }
}
