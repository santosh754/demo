package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdditionController {
    @GetMapping("/api/add")
    public int addNumbers(@RequestParam int num1, @RequestParam int num2) {
        return num1 + num2;
    }
}
