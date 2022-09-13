package ru.hookaorder.backend.feature.order.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hookaorder.backend.feature.order.entity.OrderEntity;
import ru.hookaorder.backend.feature.order.repository.OrderRepository;
import ru.hookaorder.backend.feature.roles.entity.ERole;
import ru.hookaorder.backend.feature.user.repository.UserRepository;
import ru.hookaorder.backend.utils.NullAwareBeanUtilsBean;

@RestController
@RequestMapping(value = "/order")
@Api(description = "Контроллер заказов")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @GetMapping("/get/{id}")
    @ApiOperation("Получение заказа по id")
    ResponseEntity<?> getOrderById(@PathVariable Long id, Authentication authentication) {
        return orderRepository.findById(id).map((val) -> {
            if (val.getUserId().getId().equals(id) || authentication.getAuthorities().contains(ERole.ADMIN)) {
                return ResponseEntity.ok().body(val);
            }
            return ResponseEntity.badRequest().body("Access denied");
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get/all")
    @ApiOperation("Получение всех заказов")
    ResponseEntity<Iterable<OrderEntity>> getAllOrders(@RequestBody(required = false) Long currentPlaceId, Authentication authentication) {
        var roles = authentication.getAuthorities();
        if (roles.contains(ERole.ADMIN)) {
            return ResponseEntity.ok(orderRepository.findAll());
        } else if (roles.contains(ERole.OWNER) || roles.contains(ERole.HOOKAH_MASTER) || roles.contains(ERole.WAITER)) {
            var place = userRepository.findById((Long) authentication.getPrincipal()).get().getWorkPlaces().stream().filter((val) -> val.getId().equals(currentPlaceId)).findFirst().orElseThrow();
            return ResponseEntity.ok().body(orderRepository.findAllByPlaceId(place));
        } else {
            return ResponseEntity.ok().body(orderRepository.findAllByUserId(userRepository.findById((Long) authentication.getPrincipal()).get()));
        }
    }

    @PostMapping("/create")
    @ApiOperation("Создание заказа")
    ResponseEntity<OrderEntity> createOrder(@RequestBody OrderEntity orderEntity) {
        return ResponseEntity.ok(orderRepository.save(orderEntity));
    }

    @PostMapping("/update/{id}")
    @ApiOperation("Обновление заказа по id")
    ResponseEntity<?> updateOrder(@PathVariable Long id, Authentication authentication) {
        return orderRepository.findById(id).map((val) -> {
            if (val.getUserId().getId().equals(id) || authentication.getAuthorities().contains(ERole.ADMIN)) {
                NullAwareBeanUtilsBean.copyNoNullProperties(orderRepository.findById(id), val);
                return ResponseEntity.ok().body(orderRepository.save(val));
            }
            return ResponseEntity.badRequest().body("Access denied");
        }).orElse(ResponseEntity.badRequest().build());
    }
}
