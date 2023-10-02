package com.example.dockercli.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;

@Slf4j
@RequiredArgsConstructor
public class FileUtil {

    private static final ObjectMapper objectMapper = getObjectMapper();

    public static ObjectMapper getObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        return objectMapper;
    }

    public static  <T> boolean writeFile(String filePath, T target) {
        boolean isSuccess = false;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            String payload = objectMapper.writeValueAsString(target);
            bufferedWriter.write(payload);
            isSuccess = true;
            bufferedWriter.close();

        } catch (IOException e) {
            log.debug("Write File Error - error : ", e);
        }
        return isSuccess;
    }

    public static  <T> Object getByLine(String filePath, T t) {
        T result = null;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String val = stringBuilder.toString();
            result = objectMapper.readValue(val, new TypeReference<T>() {
            });

        } catch (IOException e) {
            log.debug("Read File Error - error : {}", e.getMessage());
        }
        return result;
    }

    public static <T> T getByAllLine(String filePath, T t) {
        T result = null;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String val = stringBuilder.toString();
            result = objectMapper.readValue(val, new TypeReference<T>() {
            });

        } catch (IOException e) {
            log.debug("Read File Error - error : {}", e.getMessage());
        }
        return result;
    }

}