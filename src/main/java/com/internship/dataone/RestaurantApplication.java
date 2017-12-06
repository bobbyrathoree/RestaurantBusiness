package com.internship.dataone;

import com.internship.dataone.config.ApplicationHealthCheck;
import com.internship.dataone.config.RestaurantConfiguration;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import com.internship.dataone.resource.MyRestaurantResource;

import javax.sql.DataSource;

public class RestaurantApplication extends Application<RestaurantConfiguration> {


    public static void main(String[] args) throws Exception {
        new RestaurantApplication().run(args);
    }

    @Override
    public void run(RestaurantConfiguration configuration, Environment environment) {

        // Datasource configuration
        final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), Constants.SQL);
        DBI dbi = new DBI(dataSource);

        // Register Health Check
        ApplicationHealthCheck healthCheck = new ApplicationHealthCheck(dbi.onDemand(MyRestaurantResource.class));
        environment.healthChecks().register(Constants.SERVICE, healthCheck);

        // register my com.internship.dataone.resource
        environment.jersey().register((dbi.onDemand(MyRestaurantResource.class)));


    }
}
