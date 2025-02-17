package com.example.MultiTenant.filter;

import com.example.MultiTenant.service.JwtUtil;
import com.example.MultiTenant.tenant.TenantContext;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(TenantFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("Authorization");

        if(token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String tenantName = jwtUtil.extractTenant(token);
            TenantContext.setCurrentTenant(tenantName);
        }

        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            TenantContext.clear();
        }

    }
}
