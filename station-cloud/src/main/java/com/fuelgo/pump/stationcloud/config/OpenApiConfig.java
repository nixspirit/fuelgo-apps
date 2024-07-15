package com.fuelgo.pump.stationcloud.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        String[] packages = {"com.fuelgo.pump.stationcloud.http"};
        return GroupedOpenApi.builder()
                .group("public")
                .packagesToScan(packages)
                .build();
    }
}
