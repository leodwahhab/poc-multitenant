package com.example.MultiTenant.config;

import com.example.MultiTenant.service.TenantService;
import com.example.MultiTenant.model.Tenant;
import com.example.MultiTenant.tenant.TenantContext;
import com.example.MultiTenant.tenant.TenantRoutingDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Configuration
public class DataSourceConfig {
    Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    TenantService tenantService;

    @Bean
    public DataSource dataSource() {
        TenantRoutingDataSource dataSource = new TenantRoutingDataSource();
        Map<Object, Object> targetTenants = new HashMap<>();

        try{
//            Class.forName("TenantContext");

            List<Tenant> tenants = tenantService.getAllTenants();

            tenants.forEach(tenant -> {
                logger.info("Configurando DataSource para tenant: {}", tenant.getTenantName());
                targetTenants.put(
                        tenant.getTenantName(),
                        new HikariDataSource(
                                (HikariConfig) DataSourceBuilder.create()
                                        .url("jdbc:postgresql://localhost:5432/" + tenant.getTenantName())
                                        .username("postgres")
                                        .password("postgres")
                                        .build()
                        )
                );
            });
        } catch (RuntimeException e) {
            logger.error("Erro ao configurar os DataSources: ", e);
        }

        dataSource.setTargetDataSources(targetTenants);
        dataSource.setDefaultTargetDataSource(defaultDataSource());
        logger.info("DataSource padrão configurado.");

        return dataSource;
    }

    public DataSource defaultDataSource() {
        return new
                HikariDataSource((HikariConfig) DataSourceBuilder.create()
                        .url("jdbc:postgresql://localhost:5432/core")
                        .username("postgres")
                        .password("postgres")
                        .build()
                );
    }
}
