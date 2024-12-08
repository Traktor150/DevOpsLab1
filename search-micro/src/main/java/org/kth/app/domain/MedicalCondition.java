package org.kth.app.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "medical_condition", schema = "journaldb")
public class MedicalCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @Column(name = "diagnosed_at")
    private Date diagnosedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient; // Koppling till patient

    @ManyToOne
    @JoinColumn(name = "practitioner_id", nullable = false)
    private Practitioner practitioner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDiagnosedAt() {
        return diagnosedAt;
    }

    public void setDiagnosedAt(Date diagnosedAt) {
        this.diagnosedAt = diagnosedAt;
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
