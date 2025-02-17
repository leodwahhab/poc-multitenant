package com.example.MultiTenant.tenant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class TenantRoutingDataSource extends AbstractRoutingDataSource {
    Logger logger = LoggerFactory.getLogger(TenantRoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        String currentTenant = TenantContext.getCurrentTenant();

        if(getResolvedDataSources().containsKey(currentTenant))
            logger.info("Chave encontrada! usando tenant: {}", currentTenant);
        else
            logger.warn("Tenant '{}' não encontrado.", currentTenant);

        return currentTenant;
    }
}
