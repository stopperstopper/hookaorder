package ru.hookaorder.backend.feature.order.repository;

import org.springframework.data.repository.CrudRepository;
import ru.hookaorder.backend.feature.order.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    Optional<OrderEntity> findById(Long id);

    List<OrderEntity> findAll();

    void deleteById(Long id);

    OrderEntity save(OrderEntity placeEntity);
}
