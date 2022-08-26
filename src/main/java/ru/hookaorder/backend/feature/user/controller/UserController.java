package ru.hookaorder.backend.feature.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hookaorder.backend.feature.user.entity.UserEntity;
import ru.hookaorder.backend.feature.user.repository.UserRepository;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/get/{id}")
    ResponseEntity getUserById(@PathVariable Long id) {
        return userRepository
                .findById(id)
                .map((userEntity) -> ResponseEntity.ok().body(userEntity))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/create")
    ResponseEntity createUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok().body(userRepository.save(user));
    }

    @PutMapping(value = "/update/{id}")
    ResponseEntity updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        return userRepository
                .findById(id)
                .map((userEntity) -> ResponseEntity.ok().body(userEntity))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/get/all")
    ResponseEntity getAllUsers() {
        return ResponseEntity.ok().body(userRepository.findAll());
    }
}