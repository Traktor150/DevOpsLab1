package com.kth.journal.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NoteRequest {
    @NotNull
    private Long patientId;
    @NotNull
    private String noteContent;
}
