package com.example.MultiTenant.tenant;

import com.example.MultiTenant.config.DataSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class TenantRoutingDataSource extends AbstractRoutingDataSource {
    Logger logger = LoggerFactory.getLogger(TenantRoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        String currentTenant = TenantContext.getCurrentTenant();

        if (currentTenant == null) {
            logger.warn("Nenhum tenant encontrado no contexto. Usando DataSource padrão.");
        } else {
            logger.info("Usando tenant: {}", currentTenant);
        }

        return currentTenant;
    }
}
