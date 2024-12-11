package org.kth.app.controller;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kth.app.service.PatientService;

@Path("/api/patient-search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PatientController {
    @Inject
    PatientService patientService;

    // Endpoint to search patients by partial name
    @GET
    @Path("/by-name")
    public Uni<Response> searchByPartialName(@QueryParam("name") String name) {
        return patientService.searchPatientsByPartialName(name)
                .collect().asList() // Collect Multi into a List
                .onItem().transform(patients -> Response.ok(patients).build()); // Transform the result into a Response
    }

    // Endpoint to search patients by partial email
    @GET
    @Path("/by-email")
    public Uni<Response> searchByPartialEmail(@QueryParam("email") String email) {
        return patientService.searchPatientsByPartialEmail(email)
                .collect().asList() // Collect Multi into a List
                .onItem().transform(patients -> Response.ok(patients).build()); // Transform the result into a Response
    }

    // Endpoint to search patients by partial condition
    @GET
    @Path("/by-condition")
    public Uni<Response> searchByPartialCondition(@QueryParam("condition") String condition) {
        return patientService.searchPatientsByPartialCondition(condition)
                .collect().asList() // Collect Multi into a List
                .onItem().transform(patients -> Response.ok(patients).build()); // Transform the result into a Response
    }
}

