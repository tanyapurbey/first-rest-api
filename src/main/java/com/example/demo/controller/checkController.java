package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class checkController {
    @GetMapping("/check")
    public String check(){
        return "ok";
    }

}
