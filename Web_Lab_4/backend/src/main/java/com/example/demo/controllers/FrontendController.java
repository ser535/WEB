package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

    @RequestMapping({"/auth", "/app"})
    public String forwardSpa() {
        return "forward:/index.html";
    }
}
