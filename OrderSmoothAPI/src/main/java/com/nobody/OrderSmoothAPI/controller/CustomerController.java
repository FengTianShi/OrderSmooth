package com.nobody.OrderSmoothAPI.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nobody.OrderSmoothAPI.dto.CreateCustomerDTO;
import com.nobody.OrderSmoothAPI.dto.CustomerOrdersDTO;
import com.nobody.OrderSmoothAPI.dto.common.HttpResponseDTO;
import com.nobody.OrderSmoothAPI.entity.Customer;
import com.nobody.OrderSmoothAPI.entity.Order;
import com.nobody.OrderSmoothAPI.mapper.OrderMapper;
import com.nobody.OrderSmoothAPI.service.CustomerService;
import com.nobody.OrderSmoothAPI.service.EmailService;

import io.swagger.annotations.ApiOperation;

@RestController
public class CustomerController {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmailService emailService;

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    /**
     * Get Customer By Id
     * 
     * @param id
     * @return
     */
    @ApiOperation("Get Customer By ID")
    @GetMapping("/customer/{customerId}")
    public String getCustomerById(@PathVariable String customerId) {
        return "customer : " + customerId;
    }

    @GetMapping("/customer")
    public List<Customer> getAllCustomer() {
        return customerService.getAllCustomer();
    }

    @GetMapping("/customerOrders/{customerId}")
    public CustomerOrdersDTO getCustomerOrders(@PathVariable String customerId) {
        CustomerOrdersDTO dto = customerService.getCustomerOrders(customerId);
        // for (OrderDTO order : dto.getOrderList()) {
        // System.out.println(order.getOrderTime().atZoneSameInstant(ZoneId.systemDefault()));
        // }
        return dto;
    }

    @PostMapping("/customer")
    public HttpResponseDTO createCustomer(@Valid @RequestBody CreateCustomerDTO createCustomerDTO) {
        customerService.createCustomer(createCustomerDTO);
        return HttpResponseDTO.builder().code(200).build();
    }

    @PutMapping("/customer")
    public String updateCustomer(Customer customer) {
        return customer.getCustomerId().toString();
    }

    @DeleteMapping("/customer/{customerId}")
    public String deleteCustomerById(@PathVariable String customerId) {
        return "customer : " + customerId;
    }

    @GetMapping("/order")
    public List<Order> getAllOrder() {
        return orderMapper.selectList(null);
    }

    @GetMapping("/mail")
    public HttpResponseDTO mailTest() {
        emailService.sendMail("futenji923@gmail.com", "test mail", "hello world");
        return HttpResponseDTO.builder()
                .code(200)
                .msg("OK")
                .build();
    }
}
