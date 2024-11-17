package com.kth.journal.Services;

import com.kth.journal.Repository.ConditionRepository;
import com.kth.journal.Repository.PatientRepository;
import com.kth.journal.Repository.PractitionerRepository;
import com.kth.journal.Services.Interfaces.ConditionServiceInterface;
import com.kth.journal.domain.MedicalCondition;
import com.kth.journal.domain.Patient;
import com.kth.journal.domain.Practitioner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class ConditionService implements ConditionServiceInterface {
    private final ConditionRepository conditionRepository;
    private final PatientRepository patientRepository;
    private final PractitionerRepository practitionerRepository;

    @Override
    public MedicalCondition createCondition(Long patientId, Long practitionerId, String diagnosisName, String diagnosisDesc) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Practitioner practitioner = practitionerRepository.findById(practitionerId)
                .orElseThrow(() -> new RuntimeException("Practitioner not found"));

        MedicalCondition condition = new MedicalCondition();
        condition.setPatient(patient);
        condition.setPractitioner(practitioner);
        condition.setName(diagnosisName);
        condition.setDescription(diagnosisDesc);
        condition.setDiagnosedAt(new Date());

        return conditionRepository.save(condition);
    }
}
