package com.internship.dataone.config;

import com.codahale.metrics.health.HealthCheck;
import com.internship.dataone.resource.MyRestaurantResource;

public class ApplicationHealthCheck extends HealthCheck {
    private static final String HEALTHY = "Healthy for read and write";
    private static final String UNHEALTHY = "Not healthy";

    private final MyRestaurantResource serviceCheck;

    public ApplicationHealthCheck(MyRestaurantResource rest) {
        this.serviceCheck = rest;
    }

    @Override
    public Result check() throws Exception {
        String healthStatus = serviceCheck.performHealthCheck();

        if (healthStatus == null) {
            return Result.healthy(HEALTHY);
        } else {
            return Result.unhealthy(UNHEALTHY , healthStatus);
        }
    }
}

