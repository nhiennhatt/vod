package com.hiennhatt.vod.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/")
@RestController
public class HomeController {
    @GetMapping("")
    public Map<String, String> home() {
        return Map.of("message", "Welcome to VOD");
    }
}
