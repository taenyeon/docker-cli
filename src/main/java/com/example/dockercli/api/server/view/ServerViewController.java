package com.example.dockercli.api.server.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/server/")
@RequiredArgsConstructor
@Slf4j
public class ServerViewController {

    public String getServersPage(){
        return "";
    }

    public String getServerPage(){
        return "";
    }

    public String addServerPage(){
        return "";
    }

}
