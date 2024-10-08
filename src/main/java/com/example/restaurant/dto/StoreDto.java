package com.example.restaurant.dto;

import com.example.restaurant.domain.Store;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {
    private Long id;
    private String name;
    private String address;
    private String phone;

    public static StoreDto fromEntity(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .phone(store.getPhone())
                .build();
    }

    public Store toEntity() {
        return new Store(
                this.id,
                this.name,
                this.address,
                this.phone
        );
    }

}
