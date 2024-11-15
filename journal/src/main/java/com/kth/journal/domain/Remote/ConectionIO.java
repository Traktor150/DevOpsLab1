package com.kth.journal.domain.Remote;

import java.util.List;
import java.util.stream.Collectors;

import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.stereotype.Service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;

// TODO: throw exception if patient not found
// TODO: handle pagination
@Service
public class ConectionIO {

    private final FhirContext fhirContext;

    private final String baseUrl = "https://hapi-fhir.app.cloud.cbh.kth.se/fhir";

    private final IGenericClient client;

    public ConectionIO() {
        fhirContext = FhirContext.forR4();
        client = fhirContext.newRestfulGenericClient(baseUrl);
    }

    public IGenericClient getClient() {
        return client;
    }

    // get conditions by patient id
    public List<ModelCondition> getConditionsByPatientId(String id) {
        return client.search().forResource("Condition").where(Patient.IDENTIFIER.exactly().code(id))
                .returnBundle(org.hl7.fhir.r4.model.Bundle.class).execute().getEntry().stream()
                .map(e -> new ModelCondition((org.hl7.fhir.r4.model.Condition) e.getResource()))
                .collect(Collectors.toList());
    }

    // get all encounters by patient id
    public List<ModelEncounter> getEncountersByPatientId(String id) {
        return client.search().forResource("Encounter").where(Patient.IDENTIFIER.exactly().code(id))
                .returnBundle(org.hl7.fhir.r4.model.Bundle.class).execute().getEntry().stream()
                .map(e -> new ModelEncounter((org.hl7.fhir.r4.model.Encounter) e.getResource()))
                .collect(Collectors.toList());
    }

    // get all locatinos
    public List<ModelLocation> getAllLocations() {
        return client.search().forResource("Location").returnBundle(org.hl7.fhir.r4.model.Bundle.class).execute()
                .getEntry().stream().map(e -> new ModelLocation((org.hl7.fhir.r4.model.Location) e.getResource()))
                .collect(Collectors.toList());
    }

    // get all observations by patient id
    public List<ModelObservation> getObservationsByPatientId(String id) {
        return client.search().forResource("Observation").where(Patient.IDENTIFIER.exactly().code(id))
                .returnBundle(org.hl7.fhir.r4.model.Bundle.class).execute().getEntry().stream()
                .map(e -> new ModelObservation((org.hl7.fhir.r4.model.Observation) e.getResource()))
                .collect(Collectors.toList());
    }

    // get organisation by id
    public ModelOrganization getOrganizationById(String id) {
        return new ModelOrganization(
                client.read().resource(org.hl7.fhir.r4.model.Organization.class).withId(id).execute());
    }

    // get all organizations
    public List<ModelOrganization> getAllOrganizations() {
        return client.search().forResource("Organization").returnBundle(org.hl7.fhir.r4.model.Bundle.class).execute()
                .getEntry().stream()
                .map(e -> new ModelOrganization((org.hl7.fhir.r4.model.Organization) e.getResource()))
                .collect(Collectors.toList());
    }

    // get all patients
    public List<ModelPatient> getAllPatients() {
        return client.search().forResource("Patient").returnBundle(org.hl7.fhir.r4.model.Bundle.class).execute()
                .getEntry()
                .stream().map(e -> new ModelPatient((Patient) e.getResource())).collect(Collectors.toList());
    }

    public ModelPatient getPatientById(String id) {
        return new ModelPatient(client.read().resource(Patient.class).withId(id).execute());
    }

    // get practitioner by id
    public ModelPractitioner getPractitionerById(String id) {
        return new ModelPractitioner(
                client.read().resource(Practitioner.class).withId(id).execute());
    }

}
