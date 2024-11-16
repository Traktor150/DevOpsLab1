package com.kth.journal.Services;

import com.kth.journal.Security.SecurityConfig;
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

import java.security.Security;
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
        Optional<Account> user = userService.getUserByEmail(requsterEmail);

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        if (user.get().getRole() == SecurityConfig.PATIENT) {
            throw new RuntimeException("User does not have permission to access this resource");
        }

        List<ModelPatient> patients = conectionIO.getAllPatients();

        return patients;
    }

    public ModelPatient getPatient(String id, String requsterEmail) {
        Optional<Account> user = userService.getUserByEmail(requsterEmail);

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        // check if user is not a patient
        if (user.get().getRole() == SecurityConfig.PATIENT) {
            throw new RuntimeException("User does not have permission to access this resource");
        }

        return null;
    }

    public ModelPractitioner getPractitioner(String email) {
        ModelPractitioner practitioner = conectionIO.getPractitionerById(email);

        return practitioner;
    }
}
