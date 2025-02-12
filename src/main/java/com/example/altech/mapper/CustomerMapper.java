package com.example.altech.mapper;

import com.example.altech.dto.CustomerDTO;
import com.example.altech.model.Customer;

/**
 * Customer mapper.
 */
public class CustomerMapper {

    private CustomerMapper() {
    }

    /**
     * Convert Customer to CustomerDTO.
     *
     * @param customer
     * @return CustomerDTO
     */
    public static CustomerDTO toDto(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .build();
    }

}
