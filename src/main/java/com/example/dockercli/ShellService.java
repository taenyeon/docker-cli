package com.example.dockercli;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShellService {

    public String getContainerInfo() {
        ObjectMapper objectMapper = new ObjectMapper();
        String command = "docker stats --no-stream --format \\\n" +
                "    \"{\\\"name\\\":\\\"{{ .Name }}\\\",\\\"id\\\":\\\"{{ .Container }}\\\",\\\"memory\\\":{\\\"raw\\\":\\\"{{ .MemUsage }}\\\",\\\"percent\\\":\\\"{{ .MemPerc }}\\\"},\\\"cpu\\\":\\\"{{ .CPUPerc }}\\\"}\"";
        Process exec = null;
        String result;
        Map<String , List<String>> containerMap = new HashMap<>();
        try {
            String[] cmd = {"/bin/zsh","-c", command};
            exec = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            while ((result = bufferedReader.readLine()) != null) {
                String resultString = new String(result);
                Container container = objectMapper.readValue(resultString, Container.class);
                log.info(container.toString());
            }
            exec.waitFor();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (exec != null) {
                exec.destroy();
            }
        }
        return result;
    }
}
