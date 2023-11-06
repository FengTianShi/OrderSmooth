package com.nobody.OrderSmoothAPI.entity;

import java.io.Serializable;
import java.time.LocalDate;

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
@TableName("t_customer")
public class Customer implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String customerId;

    private String customerName;

    private LocalDate customerBir;

    private Boolean customerGander;

    private String customerPhone;

    private String customerAdd;

}
