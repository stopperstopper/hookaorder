package ru.hookaorder.backend.feature.staff.controller;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hookaorder.backend.feature.place.repository.PlaceRepository;
import ru.hookaorder.backend.feature.user.repository.UserRepository;
import ru.hookaorder.backend.utils.CheckOwnerAndRolesAccess;

import java.util.Set;

@RequestMapping(value = "/place")
@RestController
@AllArgsConstructor
public class StaffController {
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    @PostMapping("/staff/add/{placeId}")
    public ResponseEntity<?> addStaffToPlace(@PathVariable Long placeId, @RequestBody Set<Long> users, Authentication authentication) {
        return placeRepository.findById(placeId).map((val) -> {
            if (CheckOwnerAndRolesAccess.isOwnerOrAdmin(val, authentication)) {
                val.setStaff(Sets.newHashSet(userRepository.findAllById(users)));
                return ResponseEntity.ok().body(placeRepository.save(val));
            }
            return ResponseEntity.badRequest().body("Access denied");
        }).orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    @PostMapping("/staff/delete/{id}")
    public ResponseEntity<?> deleteStuffFromPlace(@PathVariable Long id, @RequestBody Set<Long> users, Authentication authentication) {
        return placeRepository.findById(id).map((val) -> {
            if (CheckOwnerAndRolesAccess.isOwnerOrAdmin(val, authentication)) {
                val.getStaff().removeAll(Sets.newHashSet(userRepository.findAllById(users)));
                return ResponseEntity.ok().body(placeRepository.save(val));
            }
            return ResponseEntity.badRequest().body("Access denied");
        }).orElse(ResponseEntity.notFound().build());
    }
}
