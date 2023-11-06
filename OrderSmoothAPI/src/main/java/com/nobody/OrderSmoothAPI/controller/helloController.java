package com.nobody.OrderSmoothAPI.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nobody.OrderSmoothAPI.entity.Customer;

@RestController
public class helloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello !";
    }

    @GetMapping("/hello1/*")
    public String hello1() {
        return "Hello 1 !";
    }

    @GetMapping("/hello2/**")
    public String hello2() {
        return "Hello 2 !";
    }

    @GetMapping("/hello3")
    public String hello3(Integer id, String name) {
        return "Hello 3 ! " + id + " " + name;
    }

    @GetMapping(value = "/hello4")
    public String hello4(Integer id, @RequestParam(value = "firstName") String name) {
        return "Hello 4 ! " + id + " " + name;
    }

    @GetMapping(value = "/hello5")
    public String hello5(Integer id, @RequestParam(value = "firstName", required = false) String name) {
        return "Hello 5 ! " + id + " " + name;
    }

    @PostMapping(value = "/hello6")
    public String hello6(Integer id, String name) {
        return "Hello 6 ! " + id + " " + name;
    }

    @PostMapping("/hello7")
    public String hello7(Customer customer) {
        return "Hello 7 ! "
                + customer.getCustomerId()
                + " "
                + customer.getCustomerName();
    }

    @PostMapping("/hello8")
    public String hello8(@RequestBody Customer customer) {
        return "Hello 8 ! "
                + customer.getCustomerId()
                + " "
                + customer.getCustomerName();
    }

}
