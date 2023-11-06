package com.nobody.OrderSmoothAPI.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.common.DateUtil;
import com.nobody.OrderSmoothAPI.common.UUIDUtil;
import com.nobody.OrderSmoothAPI.dto.CreateCustomerDTO;
import com.nobody.OrderSmoothAPI.dto.CustomerOrdersDTO;
import com.nobody.OrderSmoothAPI.entity.Customer;
import com.nobody.OrderSmoothAPI.entity.Order;
import com.nobody.OrderSmoothAPI.mapper.CustomerMapper;

@Service
public class CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerMapper customerMapper;

    @Transactional
    public Integer createCustomer(CreateCustomerDTO createCustomerDTO) {
        return customerMapper.insert(
                Customer.builder()
                        .customerId(UUIDUtil.getUUID())
                        .customerName(createCustomerDTO.getCustomerName())
                        .customerAdd(createCustomerDTO.getCustomerAdd())
                        .customerBir(DateUtil.convertToLocalDate(createCustomerDTO.getCustomerBir()))
                        .customerGander(createCustomerDTO.getCustomerGander() == 1 ? true : false)
                        .customerPhone(createCustomerDTO.getCustomerPhone())
                        .build());
    }

    public List<Customer> getAllCustomer() {
        logger.debug("Debug message");
        logger.info("Information message");
        logger.warn("Warning message");
        logger.error("Error message");
        return customerMapper.selectList(null);
    }

    public CustomerOrdersDTO getCustomerOrders(String customerId) {
        return customerMapper.selectJoinOne(
                CustomerOrdersDTO.class,
                new MPJLambdaWrapper<Customer>()
                        .selectAll(Customer.class)
                        .selectCollection(Order.class, CustomerOrdersDTO::getOrderList)
                        .leftJoin(Order.class, Order::getCustomerId, Customer::getCustomerId)
                        .eq(Customer::getCustomerId, customerId));
    }

}