package org.jeet.JeetCode.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JeetCodeController {
    public JeetCodeController(){

    }

    @GetMapping(path = "/hello")
    public String helloWorld(){
        return "Hello world";
    }
}
