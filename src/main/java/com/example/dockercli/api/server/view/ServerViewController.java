package com.example.dockercli.api.server.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/server")
@RequiredArgsConstructor
@Slf4j
public class ServerViewController {

    @GetMapping()
    public String getServersPage(){
        return "server/list";
    }

    @GetMapping("/{serverName}")
    public String getServerPage(@PathVariable String serverName, HttpServletRequest request){
        request.setAttribute("serverName", serverName);
        return "";
    }

    @GetMapping("/add")
    public String addServerPage(){
        return "server/add";
    }

}
