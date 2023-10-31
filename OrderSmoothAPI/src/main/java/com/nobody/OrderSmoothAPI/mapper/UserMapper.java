package com.nobody.OrderSmoothAPI.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.nobody.OrderSmoothAPI.entity.User;

@Mapper
public interface UserMapper {

    @Insert("insert into public.user values(#{id}, #{name})")
    public int add(User user);

    @Select("select * from public.user")
    public List<User> findAll();
}
