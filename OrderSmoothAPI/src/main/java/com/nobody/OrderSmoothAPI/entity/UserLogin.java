package com.nobody.OrderSmoothAPI.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class UserLogin {

    @TableId(type = IdType.ASSIGN_UUID)
    private String userLoginId;

    private Long userId;

    private String userMail;

    private String userPass;

    public String getUserLoginId() {
        return userLoginId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserMail() {
        return userMail;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    @Override
    public String toString() {
        return "UserLogin [userLoginId=" + userLoginId + ", userId=" + userId + ", userMail=" + userMail + ", userPass="
                + userPass + "]";
    }

}
