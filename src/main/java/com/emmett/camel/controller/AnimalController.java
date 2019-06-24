package com.emmett.camel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AnimalController {

    @GetMapping("/cat")
    public @ResponseBody String cat() {
        return "{ 'message': 'cat response'}";
    }

    @GetMapping("/dog")
    public @ResponseBody String dog() {
        return "{ 'message': 'dog response'}";
    }

    @GetMapping("/shark")
    public @ResponseBody String shark() {
        return "{ 'message': 'shark response'}";
    }
}
