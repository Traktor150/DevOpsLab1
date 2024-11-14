package com.kth.journal.domain.Remote;

import org.hl7.fhir.r4.model.Location;

public class ModelLocation {

    private String id;

    private String name;
    private String description;

    public ModelLocation(Location location) {
        this.id = location.getId();
        this.name = location.getName();
        this.description = location.getDescription();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
