package com.company.hilton;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;

import com.company.hilton.HiltonConfiguration;
import com.company.hilton.cache.CacheStore;
import com.company.hilton.dao.GeoLocationDAO;
import com.company.hilton.entity.GeoLocation;
import com.company.hilton.resources.GeolocationController;
import com.company.hilton.service.GeolocationService;

import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HiltonApplication extends Application<HiltonConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HiltonApplication().run(args);
    }


    @Override
    public String getName() {
        return "demo";
    }

    private final HibernateBundle<HiltonConfiguration> hibernate = new HibernateBundle<HiltonConfiguration>(GeoLocation.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(HiltonConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<HiltonConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }


    @Override
    public void run(final HiltonConfiguration configuration,
                    final Environment environment) {
        final CacheStore<GeoLocation> cacheStore = new CacheStore<>(60, TimeUnit.SECONDS);
        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
                .build(getName());

        final GeoLocationDAO geoLocationDAO = new GeoLocationDAO(hibernate.getSessionFactory());
        final GeolocationService service =  new GeolocationService(geoLocationDAO,client,cacheStore);
        environment.jersey().register(new GeolocationController(service));

    }

}
