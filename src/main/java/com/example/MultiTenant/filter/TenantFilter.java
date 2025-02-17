package com.example.MultiTenant.filter;

import com.example.MultiTenant.tenant.TenantContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TenantFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String tenantName = ((HttpServletRequest) servletRequest).getHeader("tenantName");
        TenantContext.setCurrentTenant(tenantName);

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            TenantContext.setCurrentTenant("");
        }
    }
}
