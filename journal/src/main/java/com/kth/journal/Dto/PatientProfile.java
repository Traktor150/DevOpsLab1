package com.kth.journal.Dto;

import lombok.Data;

import java.util.List;

@Data
public class PatientProfile {
    private Long id;
    private AccountResponse account;
    private List<ConditionResponse> conditions;
    private List<NoteResponse> notes;
}
