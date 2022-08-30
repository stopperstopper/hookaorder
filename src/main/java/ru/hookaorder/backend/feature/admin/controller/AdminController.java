package ru.hookaorder.backend.feature.admin.controller;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hookaorder.backend.feature.place.repository.PlaceRepository;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    private final PlaceRepository placeRepository;

    @Autowired
    public AdminController(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @DeleteMapping("/disband/{id}")
    @Where(clause = "deleted_at IS NULL")
    @SQLDelete(sql = "UPDATE places set deleted_at = now()::timestamp where id=?")
    ResponseEntity disbandById(@PathVariable Long id) {
        placeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
