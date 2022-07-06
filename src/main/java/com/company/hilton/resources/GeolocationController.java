package com.company.hilton.resources;

import com.company.hilton.entity.GeoLocation;
import com.company.hilton.service.GeolocationService;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hiltonGeo")
@Produces(MediaType.APPLICATION_JSON)
public class GeolocationController {
    private GeolocationService service;
    public GeolocationController(GeolocationService service) {
        this.service = service;
    }
    @GET
    @Path("/{ipAddress}")
    @UnitOfWork
    public GeoLocation getGeoLocationByIP(@PathParam("ipAddress") String ipAddress) {
        return service.getGeoLocationByName(ipAddress);
    }

}
