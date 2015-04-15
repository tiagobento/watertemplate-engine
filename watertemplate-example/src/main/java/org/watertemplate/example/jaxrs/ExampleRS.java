package org.watertemplate.example.jaxrs;

import org.watertemplate.example.collection.MonthsGrid;
import org.watertemplate.example.nestedtemplates.HomePage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.time.Year;

@Path("")
public class ExampleRS {

    @GET
    @Path("/home")
    public HomePage homePage() {
        return new HomePage();
    }

    @GET
    @Path("/months/{year}")
    public MonthsGrid monthsGrid(@PathParam("year") Integer year) {
        return new MonthsGrid(Year.of(year));
    }
}
