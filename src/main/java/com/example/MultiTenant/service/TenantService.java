package com.example.MultiTenant.service;

import com.example.MultiTenant.model.Tenant;
import com.example.MultiTenant.repository.TenantRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {
    @Autowired
    TenantRepository tenantRepository;

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }
}
