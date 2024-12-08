package org.kth.app.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.kth.app.dto.SearchResponse;
import org.kth.app.service.PatientService;

import java.util.List;

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
    public Response searchByPartialName(@QueryParam("name") String name) {
        List<SearchResponse> patients = patientService.searchPatientsByPartialName(name);
        return Response.ok(patients).build();
    }

    // Endpoint to search patients by partial email
    @GET
    @Path("/by-email")
    public Response searchByPartialEmail(@QueryParam("email") String email) {
        List<SearchResponse> patients = patientService.searchPatientsByPartialEmail(email);
        return Response.ok(patients).build();
    }

    // Endpoint to search patients by partial condition
    @GET
    @Path("/by-condition")
    public Response searchByPartialCondition(@QueryParam("condition") String condition) {
        List<SearchResponse> patients = patientService.searchPatientsByPartialCondition(condition);
        return Response.ok(patients).build();
    }
}
