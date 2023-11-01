package com.nobody.OrderSmoothAPI.entity;

import java.sql.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("test_user")
public class TestUser {

    @TableId(type = IdType.AUTO)
    private Long userId;

    @TableField("user_name")
    private String userName;

    private String userDis;

    private Boolean userSex;

    private Date userBir;

    @TableField(exist = false)
    private Integer age;

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserDis() {
        return userDis;
    }

    public Boolean getUserSex() {
        return userSex;
    }

    public Date getUserBir() {
        return userBir;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserDis(String userDis) {
        this.userDis = userDis;
    }

    public void setUserSex(Boolean userSex) {
        this.userSex = userSex;
    }

    public void setUserBir(Date userBir) {
        this.userBir = userBir;
    }

    @Override
    public String toString() {
        return "TestUser [userId=" + userId + ", userName=" + userName + ", userDis=" + userDis + ", userSex=" + userSex
                + ", userBir=" + userBir + "]";
    }

}
