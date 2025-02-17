package com.example.MultiTenant.filter;

import com.example.MultiTenant.config.DataSourceConfig;
import com.example.MultiTenant.tenant.TenantContext;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(TenantFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String tenantName = httpServletRequest.getHeader("tenantName");

        logger.info("Header recebido: tenantName = {}", tenantName);

        if (tenantName != null) {
            TenantContext.setCurrentTenant(tenantName);
            logger.info("TenantContext configurado com: {}", tenantName);
        } else {
            logger.warn("Nenhum tenant encontrado no header. Usando contexto padrão.");
        }

         try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            TenantContext.clear();
        }

    }
}
