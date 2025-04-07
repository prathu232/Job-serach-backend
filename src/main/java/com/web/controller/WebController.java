package com.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = {"/", "/jobs", "/post-job", "/login", "/admin-login", "/admin"})
    public String forwardReactRoutes() {
        return "forward:/index.html";
    }
}
