package com.kth.auth.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Practitioner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account; // Koppling till användarkonto

    @OneToMany(mappedBy = "practitioner", cascade = CascadeType.ALL)
    private List<Note> notes;

    @OneToMany(mappedBy = "practitioner", cascade = CascadeType.ALL)
    private List<MedicalCondition> conditions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<MedicalCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<MedicalCondition> conditions) {
        this.conditions = conditions;
    }
}
