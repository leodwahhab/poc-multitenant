package com.example.MultiTenant.controller;

import com.example.MultiTenant.model.Usuario;
import com.example.MultiTenant.model.dto.LoginRequest;
import com.example.MultiTenant.service.TokenService;
import com.example.MultiTenant.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.function.ServerResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        this.authenticationManager.authenticate(authenticationToken);

        Usuario usuario = (Usuario) authenticationToken.getPrincipal();

        return ResponseEntity.ok().body(tokenService.gerarToken(usuario));
    }

    @PostMapping("/register") // More descriptive endpoint name
    public ResponseEntity<Usuario> registerUser(@RequestBody LoginRequest user) {
        try {
            // 1. Check if user already exists (optional but good practice)
            if (userRepository.existsByUsername(user.getUsername())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT); // Or return a custom error response
            }

            // 2. Encode the password before saving
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            // 3. Save the user to the database
            User savedUser = userRepository.save(user);

            // 4. Return the saved user (or a simplified version)
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        } catch (Exception e) {
            // 5. Handle exceptions (log and return an error response)
            e.printStackTrace(); // In a real application, use a proper logging framework
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Or a more specific error
        }
}


