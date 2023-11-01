package com.nobody.OrderSmoothAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nobody.OrderSmoothAPI.entity.TestUser;
import com.nobody.OrderSmoothAPI.entity.User;
import com.nobody.OrderSmoothAPI.entity.UserLogin;
import com.nobody.OrderSmoothAPI.mapper.TestUserMapper;
import com.nobody.OrderSmoothAPI.mapper.UserLoginMapper;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

    @Autowired
    private TestUserMapper testMapper;

    @Autowired
    private UserLoginMapper userLoginMapper;

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
    public List<TestUser> getAllUser() {
        List<TestUser> userList = testMapper.selectList(null);
        System.out.println(userList.toString());
        return userList;
    }

    @GetMapping("/userLogin")
    public List<UserLogin> getAllUserLogin() {
        List<UserLogin> List = userLoginMapper.selectList(null);
        System.out.println(List.toString());
        return List;
    }

    @PostMapping("/user")
    public String createUser(TestUser user) {
        int updRow = testMapper.insert(user);
        System.out.println(user.toString());
        return updRow >= 1 ? "success" : "faild";
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
