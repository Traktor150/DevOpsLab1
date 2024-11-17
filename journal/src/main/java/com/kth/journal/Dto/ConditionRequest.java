package com.kth.journal.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConditionRequest {
    @NotNull
    private Long patientId;
    @NotNull
    private String diagnosisName;
    @NotNull
    private String diagnosisDesc;
}
