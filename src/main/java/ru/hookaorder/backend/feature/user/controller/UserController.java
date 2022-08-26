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
        var user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user.get());
    }

    @PostMapping(value = "/create")
    ResponseEntity createUser(@RequestBody UserEntity user) {
        var userEntity = userRepository.save(user);
        return ResponseEntity.ok().body(userEntity);
    }

    @PutMapping(value = "/update/{id}")
    ResponseEntity updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        var tempUser = userRepository.findById(id);
        if (tempUser.isPresent()) {
            var userEntity = userRepository.save(user);
            return ResponseEntity.ok().body(userEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/get/all")
    ResponseEntity getAllUsers() {
        var users = userRepository.findAll();
        return ResponseEntity.ok().body(users);
    }
}