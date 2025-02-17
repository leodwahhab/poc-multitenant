package com.example.MultiTenant.service;

import com.example.MultiTenant.model.Tenant;
import com.example.MultiTenant.repository.TenantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TenantService {
    TenantRepository tenantRepository;

    public String getTenantNameById(Integer id) {
        return tenantRepository.findTenantNameById(id);
    }

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }
}
