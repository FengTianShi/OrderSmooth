package com.nobody.OrderSmoothAPI.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class OrderDTO implements Serializable{

    private String orderId;

    private OffsetDateTime orderTime;

    private Integer orderTotal;

    private String customerId;

}
