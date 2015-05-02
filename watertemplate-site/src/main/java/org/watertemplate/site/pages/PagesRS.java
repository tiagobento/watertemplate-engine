package org.watertemplate.site.pages;

import org.watertemplate.Template;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("")
public class PagesRS {

    @GET
    @Path(Documentation.PATH)
    public Template documentation() {
        return new Documentation();
    }

    @GET
    @Path(Examples.PATH)
    public Template examples() {
        return new Examples();
    }

    @GET
    @Path(QuickStart.PATH)
    public Template quickStart() {
        return new QuickStart();
    }

    @GET
    @Path(Installation.PATH)
    public Template installation() {
        return new Installation();
    }
}
