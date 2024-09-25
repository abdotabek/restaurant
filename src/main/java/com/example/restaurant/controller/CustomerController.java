package com.example.restaurant.controller;

import com.example.restaurant.domain.Customer;
import com.example.restaurant.dto.CustomerDto;
import com.example.restaurant.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto dto) {
        CustomerDto customer = new CustomerDto();
        customer.setName(dto.getName());
        customer.setAddress(dto.getAddress());
        customer.setPhone(dto.getPhone());
        customerService.addCustomer(customer);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDto dto) {
        Customer customer = customerService.updateCustomerById(id, dto);
        customer.setName(dto.getName());
        customer.setAddress(dto.getAddress());
        customer.setPhone(dto.getPhone());
        return ResponseEntity.ok(customer);

    }

    @GetMapping("/gelAll")
    public ResponseEntity<List<Customer>> getAllCustomer() {
        List<Customer> allCustomer = customerService.getAllCustomer();
        return ResponseEntity.ok(allCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteById(@PathVariable("id") Long id) {
        customerService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
