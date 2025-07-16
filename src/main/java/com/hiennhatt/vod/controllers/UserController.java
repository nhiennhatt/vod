package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.services.UserService;
import com.hiennhatt.vod.validations.RegisterUserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerUser(@RequestBody RegisterUserValidation body) {
        this.userService.registerUser(body);
    }
}
