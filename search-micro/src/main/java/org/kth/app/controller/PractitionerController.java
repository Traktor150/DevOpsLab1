package org.kth.app.controller;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kth.app.service.PractitionerService;

import java.util.List;

@Path("/api/practitioner-search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PractitionerController {
    @Inject
    PractitionerService practitionerService;

    @GET
    @Path("/by-name")
    public Uni<Response> searchByPartialName(@QueryParam("name") String name) {
        return practitionerService.searchPractitionersByPartialName(name)
                .collect().asList() // Collect Multi into a List
                .onItem().transform(practitioners -> Response.ok(practitioners).build());
    }
}
