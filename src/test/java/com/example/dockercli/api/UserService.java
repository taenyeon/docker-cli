package com.example.dockercli.api;


import com.example.dockercli.config.security.domain.User;
import com.example.dockercli.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {

    Logger log = LoggerFactory.getLogger(UserService.class);
    private User user;

    @BeforeEach
    void init() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encoded = bCryptPasswordEncoder.encode("test");
        user = User.builder()
                .username("test")
                .password(encoded)
                .admin(true)
                .name("test")
                .build();
    }

    @Test
    void add() {
        boolean isSuccess = FileUtil.writeFile(getUserFilePath(user.getUsername()), user);
        log.info("isSuccess : {}", isSuccess);
    }

    private String getUserFilePath(String username) {
        String filePath = "/Users/gimtaeyeon/fileTest/user/{username}.json";
        return filePath.replace("{username}", username);
    }

}
