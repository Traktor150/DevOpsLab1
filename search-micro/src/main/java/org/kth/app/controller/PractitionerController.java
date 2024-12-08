package org.kth.app.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kth.app.dto.SearchResponse;
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
    public Response searchByPartialName(@QueryParam("name") String name) {
        List<SearchResponse> practitioners = practitionerService.searchPractitionersByPartialName(name);
        return Response.ok(practitioners).build();
    }
}
