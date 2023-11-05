package com.example.dockercli.batch;

import com.example.dockercli.config.storage.StorageConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchService {

    private final StorageConfig storageConfig;
    public void storageUpdate() {
        log.info("[Batch.storageUpdate] Start Batch");
        storageConfig.update();
        log.info("[Batch.storageUpdate] End Batch");
    }
}
