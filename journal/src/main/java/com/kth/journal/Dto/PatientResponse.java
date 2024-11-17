package com.kth.journal.Dto;

import java.util.List;
import java.util.stream.Collectors;

import com.kth.journal.domain.Remote.ModelPatient;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;

public record PatientResponse(Long id, Long accountId, String name, String email) {
}