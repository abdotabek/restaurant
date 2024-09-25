package com.example.restaurant.service;

import com.example.restaurant.domain.Orders;
import com.example.restaurant.domain.Store;
import com.example.restaurant.dto.StoreDto;
import com.example.restaurant.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store addStore(StoreDto storeDto) {
        Store dto = new Store();
        dto.setName(storeDto.getName());
        dto.setAddress(storeDto.getAddress());
        dto.setPhone(storeDto.getPhone());
        return storeRepository.save(dto);
    }

    public List<Store> getAllStore() {
        return storeRepository.findAll();
    }

    public Store updateStoreById(Long id, StoreDto storeDto) {
        Store store = storeRepository.findById(id).orElse(null);
        store.setName(storeDto.getName());
        store.setAddress(storeDto.getAddress());
        store.setPhone(storeDto.getPhone());
        return storeRepository.save(store);
    }

    public void deleteById(Long id) {
        storeRepository.deleteById(id);
    }
}
