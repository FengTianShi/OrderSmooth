package com.nobody.OrderSmoothAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nobody.OrderSmoothAPI.entity.User;
import com.nobody.OrderSmoothAPI.mapper.UserMapper;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据id查询用户信息
     * 
     * @param id
     * @return
     */
    @ApiOperation("Get User By ID")
    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable int id) {
        return "user:" + id;
    }

    @GetMapping("/user")
    public String getAllUser() {
        List<User> userList = userMapper.findAll();
        System.out.println(userList);
        return "user";
    }

    @PostMapping("/user")
    public String createUser(User user) {
        return user.getName();
    }

    @PutMapping("/user")
    public String updateUser(User user) {
        return user.getName();
    }

    @DeleteMapping("/user/{id}")
    public String deleteUserById(@PathVariable int id) {
        return "user:" + id;
    }
}
