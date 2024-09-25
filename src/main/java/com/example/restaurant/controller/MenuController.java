package com.example.restaurant.controller;

import com.example.restaurant.domain.Menu;
import com.example.restaurant.dto.MenuDto;
import com.example.restaurant.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/create")
    public ResponseEntity<MenuDto> addMenu(@RequestBody MenuDto menuDto) {
        MenuDto addMenu = menuService.addMenu(menuDto);
        return new ResponseEntity<>(addMenu, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Menu> updateById(@PathVariable("id") Long id, @RequestBody MenuDto menuDto) {
        Menu menu = menuService.updateMenuById(id, menuDto);
        menu.setName(menuDto.getName());
        menu.setDescription(menuDto.getDescription());
        menu.setCategory(menuDto.getCategory());
        menu.setPrice(menuDto.getPrice());
        return ResponseEntity.ok(menu);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Menu>> getAllMenu() {
        List<Menu> allMenu = menuService.getAllMenu();
        return ResponseEntity.ok(allMenu);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Menu> deleteById(@PathVariable("id") Long id) {
        menuService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
