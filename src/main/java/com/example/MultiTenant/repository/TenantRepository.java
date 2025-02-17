package com.example.MultiTenant.repository;

import com.example.MultiTenant.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {
    String findTenantNameById(Integer id);
}
