package com.example.dockercli.api.container.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/container")
@Slf4j
@RequiredArgsConstructor
public class ContainerViewController {

    @GetMapping("/{containerId}")
    public String getContainerPage(@PathVariable String containerId, HttpServletRequest request) {
        request.setAttribute("containerId", containerId);
        return "container/single";
    }

    @GetMapping("/add")
    public String addContainerPage() {
        return "container/add";
    }

}
