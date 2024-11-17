package com.kth.journal.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account; // Koppling till användarkonto

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<MedicalCondition> conditions; // Lista över diagnoser

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Note> notes;

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

    public List<MedicalCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<MedicalCondition> conditions) {
        this.conditions = conditions;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
