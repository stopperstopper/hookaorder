package ru.hookaorder.backend.feature.order.repository;

import org.springframework.data.repository.CrudRepository;
import ru.hookaorder.backend.feature.order.entity.OrderEntity;
import ru.hookaorder.backend.feature.place.entity.PlaceEntity;
import ru.hookaorder.backend.feature.user.entity.UserEntity;

import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByUserId(UserEntity id);
    List<OrderEntity> findAllByPlaceId(PlaceEntity id);
}