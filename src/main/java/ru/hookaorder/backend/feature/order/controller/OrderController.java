package ru.hookaorder.backend.feature.order.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hookaorder.backend.feature.order.entity.OrderEntity;
import ru.hookaorder.backend.feature.order.repository.OrderRepository;

@RestController
@RequestMapping(value = "/order")
@Api(description = "Контроллер заказов")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;

    @GetMapping("/get/{id}")
    @ApiOperation("Получение заказа по id")
    ResponseEntity<OrderEntity> getPlaceById(@PathVariable Long id) {
        return orderRepository.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get/all")
    @ApiOperation("Получение всех заказов")
    ResponseEntity<Iterable<OrderEntity>> getAllPlaces() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @PostMapping("/create")
    @ApiOperation("Создание заказа")
    ResponseEntity<OrderEntity> createPlace(@RequestBody OrderEntity orderEntity) {
        return ResponseEntity.ok(orderRepository.save(orderEntity));
    }

    @PostMapping("/update/{id}")
    @ApiOperation("Обновление заказа по id")
    ResponseEntity<OrderEntity> updatePlace(@PathVariable Long id) {
        return orderRepository.findById(id).map((val) -> ResponseEntity.ok(orderRepository.save(val)))
                .orElse(ResponseEntity.badRequest().build());
    }
}
