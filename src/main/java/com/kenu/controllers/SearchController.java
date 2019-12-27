package com.kenu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SearchController {

    @GetMapping
    public String root() {
        return "page3";
    }

    @PostMapping("/search")
    public String find() {
        return "page4";
    }
}
