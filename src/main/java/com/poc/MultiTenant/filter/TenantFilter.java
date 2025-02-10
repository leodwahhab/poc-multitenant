package com.poc.MultiTenant.filter;

import com.poc.MultiTenant.security.JwtService;
import com.poc.MultiTenant.tentant.TenantContext;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if(token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Claims claims = JwtService.parseToken(token);
            String tenantDb = claims.get("tenant", String.class);
            TenantContext.setCurrentTenant(tenantDb);
        }

        filterChain.doFilter(request, response);
    }
}
