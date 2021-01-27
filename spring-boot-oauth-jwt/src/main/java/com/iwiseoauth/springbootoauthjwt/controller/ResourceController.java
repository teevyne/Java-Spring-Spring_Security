package com.iwiseoauth.springbootoauthjwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class ResourceController {

    @GetMapping
    public String index() {
        return "This would be a resource - protected resource of the user. It is only accessible to those with role USER";
    }
}
