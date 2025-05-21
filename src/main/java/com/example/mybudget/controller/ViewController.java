package com.example.mybudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping(value = {
            "/", "/login", "signup", "/ledger",
            "/{path:^(?!api).*$}", "/**/{path:^(?!api).*$}"
    })
    public String forward() {
        return "forward:/index.html";
    }
}
