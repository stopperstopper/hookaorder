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

    @DeleteMapping("/disband/{id}")
    @Where(clause = "deleted_at IS NULL")
    @SQLDelete(sql = "UPDATE places set deleted_at = now()::timestamp where id=?")
    ResponseEntity disbandById(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    ResponseEntity<OrderEntity> createPlace(@RequestBody OrderEntity orderEntity) {
        return ResponseEntity.ok(orderRepository.save(orderEntity));
    }

    @PostMapping("/update")
    ResponseEntity<OrderEntity> updatePlace(@RequestBody OrderEntity orderEntity) {
        return ResponseEntity.ok().body(orderRepository.save(orderEntity));
    }
}
