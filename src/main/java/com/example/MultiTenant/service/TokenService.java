package com.example.MultiTenant.service;

import com.example.MultiTenant.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {
    private final String CHAVE_SECRETA = "POC_MULTITENANT_REVIZIA";

    public String gerarToken(Usuario usuario) {
        return String.valueOf(Jwts
                .builder()
                .issuer("POC-Multitenant")
                .subject(usuario.getUsername())
                .claim("id", usuario.getId())
                .claim("tenantName", usuario.getTenant().getTenantName())
                .expiration(Date.from(Instant.now().plusSeconds(1000)))
                .signWith(SignatureAlgorithm.HS256, CHAVE_SECRETA)
        );
    }
}
