package com.nobody.OrderSmoothAPI.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nobody.OrderSmoothAPI.entity.User;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

    @ApiOperation("Get User By ID")
    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable int id) {
        return "user:" + id;
    }

    @PostMapping("/user")
    public String createUser(User user) {
        return user.getUserName();
    }

    @PutMapping("/user")
    public String updateUser(User user) {
        return user.getUserName();
    }

    @DeleteMapping("/user/{id}")
    public String deleteUserById(@PathVariable int id) {
        return "user:" + id;
    }
}
