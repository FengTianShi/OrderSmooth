package com.nobody.OrderSmoothAPI.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_order")
public class Order implements Serializable {

    @TableId(type = IdType.AUTO)
    private String orderId;

    private OffsetDateTime orderTime;

    private Integer orderTotal;

    private String customerId;

}
