package com.example.dockercli.batch;

import com.example.dockercli.config.storage.StorageConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class Batch {

    private final BatchService batchService;

    @Scheduled(cron = "0 * * * * *")
    public void storageUpdate() {
        batchService.storageUpdate();
    }
}
