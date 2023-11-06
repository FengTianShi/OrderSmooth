package com.nobody.OrderSmoothAPI.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.nobody.OrderSmoothAPI.entity.Order;

@Mapper
public interface OrderMapper extends MPJBaseMapper<Order> {
}
