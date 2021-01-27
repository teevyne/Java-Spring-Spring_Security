package com.iwiseoauth.springbootoauthjwt.controller;

import com.iwiseoauth.springbootoauthjwt.model.User;
import com.iwiseoauth.springbootoauthjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> newUser(@Valid @RequestBody User newUser) {

        if(userService.userExists(newUser.getUsername())){
            return new ResponseEntity<>("User Already Exists", HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(userService.saveUser(newUser), HttpStatus.CREATED);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getStudentByID(@PathVariable String username) {
        if(userService.userExists(username)) {
            return new ResponseEntity<>(userService.findById(username).get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
