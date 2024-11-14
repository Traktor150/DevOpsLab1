package com.kth.journal.domain.Remote;

import org.hl7.fhir.r4.model.Organization;

public class ModelOrganization {

    private String id;

    private String name;
    private String type;
    private String address;

    public ModelOrganization(Organization organization) {
        this.id = organization.getId();
        this.name = organization.getName();
        this.type = organization.getType().get(0).getCoding().get(0).getDisplay();
        this.address = organization.getAddress().get(0).getText();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

}
