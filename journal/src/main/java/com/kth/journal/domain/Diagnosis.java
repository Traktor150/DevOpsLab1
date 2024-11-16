package com.kth.journal.domain;

import jakarta.persistence.Entity;
import java.sql.Timestamp;

@Entity
public class Diagnosis {

    private String id;
    private String code;
    private String display;
    private String system;
    private Timestamp timestamp;
    private String author;

    public Diagnosis() {
    }

    public Diagnosis(String id, String code, String display, String system, Timestamp timestamp, String author) {
        this.id = id;
        this.code = code;
        this.display = display;
        this.system = system;
        this.timestamp = timestamp;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
