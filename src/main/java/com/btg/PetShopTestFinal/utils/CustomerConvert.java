package com.btg.PetShopTestFinal.utils;


import com.btg.PetShopTestFinal.modules.customers.dto.CustomerRequest;
import com.btg.PetShopTestFinal.modules.customers.dto.CustomerResponse;
import com.btg.PetShopTestFinal.modules.customers.entity.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomerConvert {
    public static Customer toEntity(CustomerRequest customerRequest){
        Customer customer = new Customer();

        customer.setIdTransaction(UUID.randomUUID().toString());
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        customer.setAddress(customerRequest.getAddress());
        customer.setPassword(customerRequest.getPassword());
        return customer;
    }

    public static CustomerResponse toResponse(Customer customer){
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setIdTransaction(customer.getIdTransaction());
        customerResponse.setName(customer.getName());
        customerResponse.setEmail(customer.getEmail());
        customerResponse.setAddress(customer.getAddress());
        return customerResponse;
    }

    public static List<CustomerResponse> toListResponse(List<Customer> listCustomer){
        if(listCustomer == null) return new ArrayList<>();

        return listCustomer.stream()
                .map(CustomerConvert::toResponse)
                .collect(Collectors.toList());
    }
}