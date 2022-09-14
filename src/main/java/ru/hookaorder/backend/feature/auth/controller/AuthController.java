package ru.hookaorder.backend.feature.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hookaorder.backend.feature.JWT.entity.JWTRequest;
import ru.hookaorder.backend.feature.JWT.entity.JWTResponse;
import ru.hookaorder.backend.feature.JWT.entity.RefreshJwtRequest;
import ru.hookaorder.backend.feature.auth.service.AuthService;

import javax.security.auth.message.AuthException;

@RestController
@RequestMapping("/auth")
@Api(tags="Контроллер авторизации")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ApiOperation("Авторизация по логину")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest authRequest) throws AuthException {
        final JWTResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/token")
    @ApiOperation("Авторизация по токену")
    public ResponseEntity<JWTResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JWTResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    @ApiOperation("Обновить токен")
    public ResponseEntity<JWTResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JWTResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
