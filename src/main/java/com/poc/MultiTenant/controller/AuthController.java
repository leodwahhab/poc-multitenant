package com.poc.MultiTenant.controller;

import com.poc.MultiTenant.model.dtos.UserDto;
import com.poc.MultiTenant.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity login(@RequestBody UserDto userDto) {
        String token = authService.authenticate(userDto.getUsername(), userDto.getPassword());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
