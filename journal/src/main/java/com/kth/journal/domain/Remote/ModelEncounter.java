package com.kth.journal.domain.Remote;

import org.hl7.fhir.r4.model.Encounter;

public class ModelEncounter {

    private String id;

    private String status;
    private String type;

    private String subjectReference;
    private String subjectDisplay;

    private String primaryPractitionerReference;
    private String primaryPractitionerDisplay;

    private String periodStart;
    private String periodEnd;

    private String serviceProviderReference;
    private String serviceProviderDisplay;

    public ModelEncounter(Encounter encounter) {
        this.id = encounter.getId();
        this.status = encounter.getStatus().getDisplay();
        this.type = encounter.getType().get(0).getCoding().get(0).getDisplay();
        this.subjectReference = encounter.getSubject().getReference();
        this.subjectDisplay = encounter.getSubject().getDisplay();
        this.primaryPractitionerReference = encounter.getParticipantFirstRep().getIndividual().getReference();
        this.primaryPractitionerDisplay = encounter.getParticipantFirstRep().getIndividual().getDisplay();
        this.periodStart = encounter.getPeriod().getStart().toString();
        this.periodEnd = encounter.getPeriod().getEnd().toString();
        this.serviceProviderReference = encounter.getServiceProvider().getReference();
        this.serviceProviderDisplay = encounter.getServiceProvider().getDisplay();
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getSubjectReference() {
        return subjectReference;
    }

    public String getSubjectDisplay() {
        return subjectDisplay;
    }

    public String getPrimaryPractitionerReference() {
        return primaryPractitionerReference;
    }

    public String getPrimaryPractitionerDisplay() {
        return primaryPractitionerDisplay;
    }

    public String getPeriodStart() {
        return periodStart;
    }

    public String getPeriodEnd() {
        return periodEnd;
    }

    public String getServiceProviderReference() {
        return serviceProviderReference;
    }

    public String getServiceProviderDisplay() {
        return serviceProviderDisplay;
    }

}
