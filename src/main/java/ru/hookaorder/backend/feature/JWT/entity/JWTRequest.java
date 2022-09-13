package ru.hookaorder.backend.feature.JWT.entity;

import lombok.Data;

@Data
public class JWTRequest {
    private String phone;
    private String password;
}
