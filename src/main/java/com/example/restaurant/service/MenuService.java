package com.example.restaurant.service;

import com.example.restaurant.domain.Menu;
import com.example.restaurant.dto.MenuDto;
import com.example.restaurant.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<Menu> getAllMenu() {
        return menuRepository.findAll();
    }

    @Transactional
    public Menu updateMenuById(Long id, MenuDto menuDto) {
        Menu menu = menuRepository.findById(id).orElse(null);
        menu.setName(menuDto.getName());
        menu.setCategory(menuDto.getCategory());
        menu.setDescription(menuDto.getDescription());
        menu.setPrice(menuDto.getPrice());
        return menuRepository.save(menu);
    }

    @Transactional
    public void deleteById(Long id) {
        menuRepository.deleteById(id);
    }

}