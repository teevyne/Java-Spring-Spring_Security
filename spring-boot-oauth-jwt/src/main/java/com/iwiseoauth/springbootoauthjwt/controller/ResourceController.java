package com.iwiseoauth.springbootoauthjwt.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class ResourceController {

    @GetMapping
    public String index() {
        //Lets return the id of any user who acceses this endpoint

//        return SecurityContextHolder.getContext().getAuthentication().getName();
//        return SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();

        String result =  SecurityContextHolder.getContext().getAuthentication().getName();

        return ("This would be a protected resource for the user" + result);
    }

    @GetMapping("/admin")
    public String admin() {


        return "This would be a resource - protected resource of the for an admin";
    }
}
