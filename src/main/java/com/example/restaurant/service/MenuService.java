package com.example.restaurant.service;

import com.example.restaurant.domain.Menu;
import com.example.restaurant.dto.MenuDto;
import com.example.restaurant.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MenuService {
    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional
    public MenuDto addMenu(MenuDto dto) {
        Menu menu = dto.toEntity();
        return MenuDto.fromEntity(menuRepository.save(menu));
    }
}