package ru.hookaorder.backend.feature.stuff.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hookaorder.backend.feature.place.repository.PlaceRepository;
import ru.hookaorder.backend.feature.user.entity.UserEntity;
import ru.hookaorder.backend.utils.CheckOwnerAndRolesAccess;

import java.util.Set;

@RequestMapping(value = "/place")
@RestController
@AllArgsConstructor
public class StuffController {
    private final PlaceRepository placeRepository;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    @PostMapping("/stuff/add/{id}")
    public ResponseEntity<?> addStuffToPlace(@PathVariable Long id, @RequestBody Set<UserEntity> users, Authentication authentication) {
        return placeRepository.findById(id).map((val) -> {
            if (CheckOwnerAndRolesAccess.isOwnerOrAdmin(val, authentication)) {
                val.setStuff(users);
                return ResponseEntity.ok().body(placeRepository.save(val));
            }
            return ResponseEntity.badRequest().body("Access denied");
        }).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    @PostMapping("/stuff/delete/{id}")
    public ResponseEntity<?> deleteStuffFromPlace(@PathVariable Long id, @RequestBody Set<UserEntity> users, Authentication authentication) {
        return placeRepository.findById(id).map((val) -> {
            if (CheckOwnerAndRolesAccess.isOwnerOrAdmin(val, authentication)) {
                val.getStuff().removeAll(users);
                return ResponseEntity.ok().body(placeRepository.save(val));
            }
            return ResponseEntity.badRequest().body("Access denied");
        }).orElse(ResponseEntity.notFound().build());
    }
}
