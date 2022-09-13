package ru.hookaorder.backend.feature.user.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.hookaorder.backend.feature.roles.entity.ERole;
import ru.hookaorder.backend.feature.user.entity.UserEntity;
import ru.hookaorder.backend.feature.user.repository.UserRepository;
import ru.hookaorder.backend.utils.NullAwareBeanUtilsBean;

@RestController
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping(value = "/get/{id}")
    @Where(clause = "is_enabled IS TRUE")
    ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userRepository.findById(id).map((val) -> ResponseEntity.ok().body(val)).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/create")
    ResponseEntity<?> createUser(@RequestBody UserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok().body(userRepository.save(user));
    }

    @PutMapping(value = "/update/{id}")
    ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody UserEntity user, Authentication authentication) {
        return userRepository.findById(id).map((val) -> {
            if (val.getId() != authentication.getPrincipal() || !authentication.getAuthorities().contains(ERole.ADMIN)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden for this user");
            }
            NullAwareBeanUtilsBean.copyNoNullProperties(user, val);
            return ResponseEntity.ok().body(userRepository.save(val));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/disable")
    @ApiOperation(value = "Деактивация аккаунта со стороны пользоватея")
    ResponseEntity<?> disableByUser(Authentication authentication) {
        UserEntity entity = userRepository.findById((Long) authentication.getPrincipal()).get();
        entity.setEnabled(false);
        userRepository.save(entity);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/get/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    @DeleteMapping("/disband/{id}")
    @SQLDelete(sql = "UPDATE places set deleted_at = now()::timestamp where id=?")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ApiOperation(value = "", hidden = true)
    ResponseEntity<?> disbandUserById(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}