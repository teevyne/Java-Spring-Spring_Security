package com.iwiseoauth.springbootoauthjwt;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @RequestMapping("/home")
    public String index() {

        return "This would be a resource - protected resource of the user";
    }
}
