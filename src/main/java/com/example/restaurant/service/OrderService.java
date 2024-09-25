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

    @Transactional
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

    @Transactional
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Orders orders = orderRepository.findById(id).orElseThrow(null);

        Customer customer = customerRepository.findById(orderDto.getCustomerId()).orElseThrow(null);

        Store store = storeRepository.findById(orderDto.getStoreId()).orElseThrow(null);

        List<OrderItem> newOrderItems = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderDto.getOrderItems()) {
            Menu menu = menuRepository.findById(orderItemDto.getMenuId()).orElse(null);
            OrderItem orderItem = orderItemDto.toEntity(orders, menu);
            newOrderItems.add(orderItem);
        }
        orderItemRepository.deleteAll(orders.getOrderItems());
        orderItemRepository.saveAll(newOrderItems);
        orders.setCustomer(customer);
        orders.setStore(store);
        orders.setOrderItems(newOrderItems);
        orders.setStatus(orderDto.getStatus());
        orders.setOrderUpdateAt(LocalDateTime.now());
        orderRepository.save(orders);
        return OrderDto.fromEntity(orders);
    }

    @Transactional
    public void cancelOrder(Long id) {
        Orders orders = orderRepository.findById(id).orElseThrow(null);
        orders.setStatus(OrderStatus.CANCELED);
        orderRepository.save(orders);
    }

    public double calculateTotalAmount(Long id) {
        Orders orders = orderRepository.findById(id).orElseThrow(null);
        return orders.getOrderItems().stream()
                .mapToDouble(orderItem -> orderItem.getMenu().getPrice() * orderItem.getQuantity()).sum();
    }

    public double completeOrder(Long id) {
        Orders orders = orderRepository.findById(id).orElseThrow(null);
        orders.setStatus(OrderStatus.CANCELED);
        orderRepository.save(orders);
        return calculateTotalAmount(id);
    }

}
