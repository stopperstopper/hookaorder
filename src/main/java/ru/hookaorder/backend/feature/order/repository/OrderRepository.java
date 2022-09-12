package ru.hookaorder.backend.feature.order.repository;

import org.springframework.data.repository.CrudRepository;
import ru.hookaorder.backend.feature.order.entity.OrderEntity;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
}
