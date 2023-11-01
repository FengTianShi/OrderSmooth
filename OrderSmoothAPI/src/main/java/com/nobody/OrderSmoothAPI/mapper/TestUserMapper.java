package com.nobody.OrderSmoothAPI.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nobody.OrderSmoothAPI.entity.TestUser;

@Mapper
public interface TestUserMapper extends BaseMapper<TestUser> {
}
