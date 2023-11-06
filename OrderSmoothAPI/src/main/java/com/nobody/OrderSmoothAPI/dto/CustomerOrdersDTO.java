package com.nobody.OrderSmoothAPI.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrdersDTO implements Serializable {

    private String customerId;

    private String customerName;

    private LocalDate customerBir;

    private Boolean customerGander;

    private String customerPhone;

    private String customerAdd;

    private List<OrderDTO> orderList;

}
