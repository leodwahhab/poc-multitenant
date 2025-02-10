package com.poc.MultiTenant.service;

import com.poc.MultiTenant.model.User;
import com.poc.MultiTenant.repository.UserRepository;
import com.poc.MultiTenant.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public String authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Senha incorreta");
        }

        return JwtService.generateToken(username, user.getTenantDatabase());
    }
}
