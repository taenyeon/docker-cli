package com.example.dockercli.api.server.controller.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class MainController {


    public String mainPage(){
        return "/";
    }
}
