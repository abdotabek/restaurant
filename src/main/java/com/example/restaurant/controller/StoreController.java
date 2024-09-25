package com.example.restaurant.controller;

import com.example.restaurant.domain.Store;
import com.example.restaurant.dto.StoreDto;
import com.example.restaurant.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/create")
    public ResponseEntity<StoreDto> createStore(@RequestBody StoreDto storeDto) {
        StoreDto store = new StoreDto();
        store.setName(storeDto.getName());
        store.setAddress(storeDto.getAddress());
        store.setPhone(storeDto.getPhone());
        storeService.addStore(store);
        return ResponseEntity.ok(store);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Store>> getAll() {
        List<Store> allStore = storeService.getAllStore();
        return ResponseEntity.ok(allStore);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Store> updateById(@PathVariable("id") Long id, @RequestBody StoreDto storeDto) {
        Store result = storeService.updateStoreById(id, storeDto);
        result.setName(storeDto.getName());
        result.setAddress(storeDto.getAddress());
        result.setPhone(storeDto.getPhone());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Store> deleteById(@PathVariable("id") Long id) {
        storeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
