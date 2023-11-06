package com.example.dockercli.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@RequiredArgsConstructor
public class FileUtil {

    private static final ObjectMapper objectMapper = getObjectMapper();

    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

    public static <T> boolean writeFile(String filePath, T target) {
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

    public static <T> ConcurrentMap<String, T> getMultiValueMappingConcurrentMap(String filePath, Class<T> t) {
        ConcurrentHashMap<String, T> result = null;
        JavaType javaType = objectMapper.getTypeFactory()
                .constructParametricType(ConcurrentHashMap.class,String.class, t);
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String val = stringBuilder.toString();
            log.info("text : {}", val);
            result = objectMapper.readValue(val, javaType);
        } catch (IOException e) {
            log.error("Read File Error - error : {}", e.getMessage());
        }
        return result;
    }

    public static <T> T getSingleValue(String filePath, Class<T> t) {
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
            log.info("text : {}", val);
            result = objectMapper.readValue(val, t);
            log.info("result : {}", result.getClass().getName());

        } catch (IOException e) {
            log.error("Read File Error - error : {}", e.getMessage());
        }
        return result;
    }


    public static String getStringValue(String filePath) {
        String val = null;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            val = stringBuilder.toString();
            log.info("text : {}", val);

        } catch (IOException e) {
            log.error("Read File Error - error : {}", e.getMessage());
        }
        return val;
    }
}
