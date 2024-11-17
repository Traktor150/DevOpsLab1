package com.kth.journal.Services.Interfaces;

import com.kth.journal.domain.MedicalCondition;

public interface ConditionServiceInterface {
    MedicalCondition createCondition(Long patientId, Long practitionerId, String diagnosisName, String diagnosisDesc);
}
