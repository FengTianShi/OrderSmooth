package com.nobody.OrderSmoothAPI.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nobody.OrderSmoothAPI.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    // @Insert("insert into public.user values(#{id}, #{name})")
    // public int add(User user);

    // @Select("select * from public.user")
    // public List<User> findAll();

    // @Insert("insert into public.user values(#{id}, #{name})")
    // public int createUser(User user);
}
