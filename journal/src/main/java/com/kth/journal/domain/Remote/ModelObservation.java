package com.kth.journal.domain.Remote;

import org.hl7.fhir.r4.model.Observation;

public class ModelObservation {
    private String id;

    private String status;
    private String category;
    private String code;

    private String subject;

    private String encounter;

    // effectiveDateTime is just issued - milliseconds
    private String effectiveDateTime;
    private String issued;

    private String valueQuantity;
    private String valueUnit;

    public ModelObservation(Observation observation) {
        this.id = observation.getId();
        this.status = observation.getStatus().getDisplay();
        this.category = observation.getCategory().get(0).getCoding().get(0).getDisplay();
        this.code = observation.getCode().getCoding().get(0).getDisplay();
        this.subject = observation.getSubject().getReference();
        this.encounter = observation.getEncounter().getReference();
        this.effectiveDateTime = observation.getEffectiveDateTimeType().getValueAsString();
        this.issued = observation.getIssued().toString();
        this.valueQuantity = observation.getValueQuantity().getValue().toString();
        this.valueUnit = observation.getValueQuantity().getUnit();
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getCategory() {
        return category;
    }

    public String getCode() {
        return code;
    }

    public String getSubject() {
        return subject;
    }

    public String getEncounter() {
        return encounter;
    }

    public String getEffectiveDateTime() {
        return effectiveDateTime;
    }

    public String getIssued() {
        return issued;
    }

    public String getValueQuantity() {
        return valueQuantity;
    }

    public String getValueUnit() {
        return valueUnit;
    }

}
