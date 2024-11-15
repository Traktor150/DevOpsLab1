package com.kth.journal.Services;

import com.kth.journal.Services.Interfaces.RemoteReposetoryServiceInterface;
import com.kth.journal.domain.Account;
import com.kth.journal.domain.Remote.ConectionIO;
import com.kth.journal.domain.Remote.ModelCondition;
import com.kth.journal.domain.Remote.ModelEncounter;
import com.kth.journal.domain.Remote.ModelLocation;
import com.kth.journal.domain.Remote.ModelObservation;
import com.kth.journal.domain.Remote.ModelOrganization;
import com.kth.journal.domain.Remote.ModelPatient;
import com.kth.journal.domain.Remote.ModelPractitioner;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class RemoteReposetoryService implements RemoteReposetoryServiceInterface {
    private final UserService userService;
    private final ConectionIO conectionIO;

    public RemoteReposetoryService(UserService userService) {
        this.userService = userService;
        this.conectionIO = new ConectionIO();
    }

    public List<ModelCondition> getConditions(Account patient, Account requster) {
        return null;
    }

    public List<ModelEncounter> getEncounters(Account patient, Account requster) {
        return null;
    }

    public List<ModelLocation> getLocations() {
        return null;
    }

    public List<ModelObservation> getObservations(Account patient, Account requster) {
        return null;
    }

    public List<ModelOrganization> getOrganizations() {
        return null;
    }

    public List<ModelPatient> getPatients(String requsterEmail) {
        if (!userService.userIsDoctor(requsterEmail)) {
            throw new RuntimeException("User is not a doctor");
        }

        List<ModelPatient> patients = conectionIO.getAllPatients();

        return patients;
    }

    public ModelPatient getPatient(String id) {
        ModelPatient patient = conectionIO.getPatientById(id);
        return patient;
    }

    public List<ModelPractitioner> getPractitioners() {
        return null;
    }
}
