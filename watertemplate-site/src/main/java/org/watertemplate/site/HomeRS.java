package org.watertemplate.site;

import org.watertemplate.site.templates.pages.home.Home;

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
