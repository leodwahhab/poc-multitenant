package com.example.MultiTenant.controller;

import com.example.MultiTenant.model.Usuario;
import com.example.MultiTenant.model.dto.AuthResponse;
import com.example.MultiTenant.model.dto.LoginRequest;
import com.example.MultiTenant.service.JwtUtil;
import com.example.MultiTenant.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioService.findByUsername(request.getUsername());
        if (usuario == null || !usuario.getPassword().equals(request.getPassword())) {
            return ResponseEntity.badRequest().body("Usuário ou senha inválidos");
        }

        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getTenant().getTenantName());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}


