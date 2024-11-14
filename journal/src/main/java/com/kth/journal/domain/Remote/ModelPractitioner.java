package com.kth.journal.domain.Remote;

import org.hl7.fhir.r4.model.Practitioner;

public class ModelPractitioner {

    private String id;

    private String familyName;
    private String givenName;

    private String telecom;

    public ModelPractitioner(Practitioner practitioner) {
        this.id = practitioner.getIdElement().getIdPart();
        this.familyName = practitioner.getNameFirstRep().getFamily();
        this.givenName = practitioner.getNameFirstRep().getGivenAsSingleString();
        this.telecom = practitioner.getTelecomFirstRep().getValue();
    }

    public String getId() {
        return id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getTelecom() {
        return telecom;
    }
}
