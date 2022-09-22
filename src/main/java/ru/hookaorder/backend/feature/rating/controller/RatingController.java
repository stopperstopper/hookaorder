package ru.hookaorder.backend.feature.rating.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hookaorder.backend.feature.rating.entity.RatingEntity;
import ru.hookaorder.backend.feature.rating.repository.RatingRepository;
import ru.hookaorder.backend.feature.user.entity.UserEntity;
import ru.hookaorder.backend.feature.user.repository.UserRepository;

@RestController
@RequestMapping(value = "/rating")
@AllArgsConstructor
public class RatingController {

    private final UserRepository userRepository;

    private final RatingRepository ratingRepository;

    @PostMapping(value = "/add/{userId}")
    ResponseEntity<?> addRatingByUserId(@RequestBody RatingEntity rating, @PathVariable Long userId) {
        RatingEntity ratingEntity = ratingRepository.save(rating);
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        userEntity.addRating(ratingEntity);
        userRepository.save(userEntity);
        return ResponseEntity.ok().body(userEntity);
    }
}
