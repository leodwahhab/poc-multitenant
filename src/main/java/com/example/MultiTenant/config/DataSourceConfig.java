package com.example.MultiTenant.config;

import com.example.MultiTenant.service.TenantService;
import com.example.MultiTenant.model.Tenant;
import com.example.MultiTenant.tenant.TenantRoutingDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Order(1)
@Configuration
public class DataSourceConfig {
    @Autowired
    TenantService tenantService;

    @Bean
    public DataSource dataSource() {
        TenantRoutingDataSource tenantRoutingDataSource = new TenantRoutingDataSource();

        List<Tenant> tenants = tenantService.getAllTenants();
        Map<Object, Object> targetTenants = new HashMap<>();

        tenants.forEach(tenant -> {
            targetTenants.put(
                tenant.getTenantName(),
                new HikariDataSource(
                    (HikariConfig) DataSourceBuilder.create()
                        .url("localhost:5432/" + tenant.getTenantName())
                        .username("postgres")
                        .password("postgres")
                        .build()
                )
            );
        });

        tenantRoutingDataSource.setTargetDataSources(targetTenants);
        tenantRoutingDataSource.setDefaultTargetDataSource(defaultDataSource());

        return tenantRoutingDataSource;
    }

    @Bean
    public DataSource defaultDataSource() {
        return new
                HikariDataSource((HikariConfig) DataSourceBuilder.create()
                        .url("localhost:5432/core")
                        .username("postgres")
                        .password("postgres")
                        .build()
                );
    }
}
