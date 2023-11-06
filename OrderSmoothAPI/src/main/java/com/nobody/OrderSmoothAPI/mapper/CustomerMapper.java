package com.nobody.OrderSmoothAPI.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.nobody.OrderSmoothAPI.entity.Customer;

@Mapper
public interface CustomerMapper extends MPJBaseMapper<Customer> {

    // @Insert("insert into public.user values(#{id}, #{name})")
    // public int add(User user);

    // @Select("select * from public.user")
    // public List<User> findAll();

    // @Insert("insert into public.user values(#{id}, #{name})")
    // public int createUser(User user);
}
