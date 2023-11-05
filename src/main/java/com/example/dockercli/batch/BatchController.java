package com.example.dockercli.batch;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
@Slf4j
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    @GetMapping("/storage/update")
    public ResponseEntity<String> manualStorageUpdate(){
        batchService.storageUpdate();
        return ResponseEntity.ok("SUCCESS");
    }

}
