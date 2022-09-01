package ru.hookaorder.backend.feature.place.controller;


import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hookaorder.backend.feature.place.entity.PlaceEntity;
import ru.hookaorder.backend.feature.place.repository.PlaceRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/place")
public class PlaceController {
    @Autowired
    private PlaceRepository placeRepository;

    @GetMapping("/get/{id}")
    ResponseEntity<PlaceEntity> getPlaceById(@PathVariable Long id) {
        return placeRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Where(clause = "deleted_at IS NULL")
    @GetMapping("/get/all")
    ResponseEntity<List<PlaceEntity>> getAllPlaces() {
        return ResponseEntity.ok(placeRepository.findAll());
    }

    @PostMapping("/create")
    ResponseEntity<PlaceEntity> createPlace(@RequestBody PlaceEntity placeEntity) {
        return ResponseEntity.ok(placeRepository.save(placeEntity));
    }

    @PostMapping("/update/{id}")
    ResponseEntity<PlaceEntity> updatePlace(@PathVariable Long id) {
        return placeRepository.findById(id).map((val) -> ResponseEntity.ok(placeRepository.save(val)))
                .orElse(ResponseEntity.badRequest().build());
    }
}
