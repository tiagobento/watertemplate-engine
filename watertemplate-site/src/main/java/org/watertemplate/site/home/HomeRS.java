package org.watertemplate.site.home;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("")
public class HomeRS {

    @GET
    @Path(Home.PATH)
    public Home home() {
        return new Home();
    }

}
