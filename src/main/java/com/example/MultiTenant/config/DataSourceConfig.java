package com.example.MultiTenant.config;

import com.example.MultiTenant.service.TenantService;
import com.example.MultiTenant.model.Tenant;
import com.example.MultiTenant.tenant.TenantRoutingDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Configuration
public class DataSourceConfig {
    Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    TenantService tenantService;

    @Bean
    public DataSource dataSource() {
        TenantRoutingDataSource dataSource = new TenantRoutingDataSource();
        Map<Object, Object> targetTenants = new HashMap<>();

        DataSource defaultDataSource = defaultDataSource();
        targetTenants.put("core", defaultDataSource);
        dataSource.setDefaultTargetDataSource(defaultDataSource);

        dataSource.setTargetDataSources(targetTenants);
        dataSource.setDefaultTargetDataSource(defaultDataSource());

        dataSource.afterPropertiesSet();

        initializeTenantDataSources(dataSource, targetTenants);

        return dataSource;
    }

    private void initializeTenantDataSources(TenantRoutingDataSource dataSource, Map<Object, Object> targetTenants) {
        CompletableFuture.runAsync(() -> {
            logger.info("Inicializando DataSources para todos os tenants...");
            try{
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
                dataSource.setTargetDataSources(targetTenants);
                dataSource.afterPropertiesSet();
                logger.info("Datasources configurados: {}", targetTenants);
            } catch (RuntimeException e) {
                logger.error("Erro ao configurar os DataSources: ", e);
            }
        });
    }

    private DataSource defaultDataSource() {
        return new
                HikariDataSource((HikariConfig) DataSourceBuilder.create()
                        .url("jdbc:postgresql://localhost:5432/core")
                        .username("postgres")
                        .password("postgres")
                        .build()
                );
    }
}
