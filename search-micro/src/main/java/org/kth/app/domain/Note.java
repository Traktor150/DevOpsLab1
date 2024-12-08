package org.kth.app.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient; // Koppling till patient

    @ManyToOne
    @JoinColumn(name = "practitioner_id", nullable = false)
    private Practitioner practitioner; // Skapare av noteringen

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Practitioner getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }
}
