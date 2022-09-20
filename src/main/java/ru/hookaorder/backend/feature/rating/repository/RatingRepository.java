package ru.hookaorder.backend.feature.rating.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.hookaorder.backend.feature.rating.entity.RatingEntity;
import ru.hookaorder.backend.feature.user.entity.UserEntity;

public interface RatingRepository extends CrudRepository<RatingEntity, Long> {

//      @Query(value = "SELECT ROUND(AVG(CAST(rating_value AS FLOAT)), 2) FROM ratings
//    WHERE rating_id IN (SELECT rating_id FROM users_ratings WHERE user_entity_id = ?1)" , nativeQuery = true)
       //     float getAverageRating(long userId);
}
