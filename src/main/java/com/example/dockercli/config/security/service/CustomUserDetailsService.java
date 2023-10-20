package com.example.dockercli.config.security.service;

import com.example.dockercli.config.security.domain.User;
import com.example.dockercli.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private String getUserFilePath(String username) {
        String filePath = "/Users/gimtaeyeon/fileTest/user/{username}.json";
        return filePath.replace("{username}", username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  FileUtil.getSingleValue(getUserFilePath(username),
                User.class);
    }

}
