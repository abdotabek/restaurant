package com.example.restaurant.dto;

import com.example.restaurant.domain.Customer;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    private Long id;
    private String name;
    private String address;
    private String phone;

    public static CustomerDto fromEntity(Customer customer) {
        return new CustomerDto().builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .build();
    }

    public Customer toEntity() {
        return new Customer(
                this.id,
                this.name,
                this.address,
                this.phone
        );
    }
}
