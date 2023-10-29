package com.nobody.OrderSmoothAPI.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nobody.OrderSmoothAPI.entity.User;
import com.nobody.OrderSmoothAPI.entity.UserTwo;

@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!!";
    }

    @GetMapping("/hello1/*")
    public String hello1() {
        return "Hello World 1 !!";
    }

    @GetMapping("/hello2/**")
    public String hello2() {
        return "Hello World 2 !!";
    }

    @GetMapping("/hello3")
    public String hello3(String name, String phone) {
        return "Hello World 3 !! " + name + " " + phone;
    }

    @GetMapping(value = "/hello4")
    public String hello4(String name, @RequestParam(value = "phoneNum") String phone) {
        return "Hello World 4 !! " + name + " " + phone;
    }

    @GetMapping(value = "/hello5")
    public String hello5(String name, @RequestParam(value = "phoneNum", required = false) String phone) {
        return "Hello World 5 !! " + name + " " + phone;
    }

    @PostMapping(value = "/hello6")
    public String hello6(String name, String phone) {
        return "Hello World 6 !! " + name + " " + phone;
    }

    @PostMapping("/hello7")
    public String hello7(User user) {
        return "Hello World 7 !! " + user.getUserName() + " " + user.getUserPhone();
    }

    @PostMapping("/hello8")
    public String hello8(@RequestBody User user) {
        return "Hello World 8 !! " + user.getUserName() + " " + user.getUserPhone();
    }

    @PostMapping("/hello9")
    public String hello9(@RequestBody UserTwo userTwo) {
        return "Hello World 9 !! " + userTwo.getUser1().getUserName() + " " + userTwo.getUser2().getUserName();
    }
}
