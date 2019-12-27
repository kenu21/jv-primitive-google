package com.kenu.controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping("/page2")
    public String submit(@RequestParam("q") String uri,
                         @RequestParam("level") Integer level) throws IOException {
        return "page2";
    }
}
