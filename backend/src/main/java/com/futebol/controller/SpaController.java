package com.futebol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {

    @GetMapping({"/", "/login", "/dashboard", "/players", "/payments", "/transactions"})
    public String forward() {
        return "forward:/index.html";
    }
}
