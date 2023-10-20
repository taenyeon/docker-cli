package com.example.dockercli.api.main.view.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class MainViewController {

    public String mainPage(){
        return "/";
    }
}
