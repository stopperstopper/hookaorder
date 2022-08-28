package ru.hookaorder.backend.feature.order.controller;


import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hookaorder.backend.feature.order.entity.OrderEntity;
import ru.hookaorder.backend.feature.order.repository.OrderRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/get/{id}")
    ResponseEntity<OrderEntity> getPlaceById(@PathVariable Long id) {
        var response = orderRepository.findById(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get/all")
    ResponseEntity<List<OrderEntity>> getAllPlaces() {
        System.out.println(orderRepository.findAll());
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @PostMapping("/create")
    ResponseEntity<OrderEntity> createPlace(@RequestBody OrderEntity orderEntity) {
        return ResponseEntity.ok(orderRepository.save(orderEntity));
    }

    @PostMapping("/update")
    ResponseEntity<OrderEntity> updatePlace(@PathVariable Long id) {
        return orderRepository.findById(id).map((val) -> ResponseEntity.ok(orderRepository.save(val)))
                .orElse(ResponseEntity.badRequest().build());
    }
}
