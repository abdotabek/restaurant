package com.example.restaurant.service;

import com.example.restaurant.domain.*;
import com.example.restaurant.dto.OrderDto;
import com.example.restaurant.dto.OrderItemDto;
import com.example.restaurant.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        CustomerRepository customerRepository, MenuRepository menuRepository,
                        StoreRepository storeRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.customerRepository = customerRepository;
        this.menuRepository = menuRepository;
        this.storeRepository = storeRepository;
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();

    }

    public OrderDto createOrder(OrderDto orderDto) {
        Customer customer = customerRepository.findById(orderDto.getCustomerId()).orElseThrow(null);

        Store store = storeRepository.findById(orderDto.getStoreId()).orElseThrow(null);

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderDto.getOrderItems()) {
            Menu menu = menuRepository.findById(orderItemDto.getMenuId()).orElse(null);
            OrderItem orderItem = orderItemDto.toEntity(null, menu);
            orderItems.add(orderItem);
        }
        Orders orders = orderDto.toEntity(customer, store, orderItems);
        for (OrderItem item : orderItems) {
            item.setOrders(orders);
        }
        orders.setOrderedAt(LocalDateTime.now());
        orders = orderRepository.save(orders);
        return OrderDto.fromEntity(orders);
    }
}
