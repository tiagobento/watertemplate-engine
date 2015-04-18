package org.watertemplate.example.site.home;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("")
public class HomeRS {

    @GET
    @Path("/home")
    public Home home() {
        return new Home();
    }

}
