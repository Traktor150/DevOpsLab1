package com.kth.journal.Services.Interfaces;

import com.kth.journal.domain.Account;
import com.kth.journal.domain.Remote.ModelCondition;
import com.kth.journal.domain.Remote.ModelEncounter;
import com.kth.journal.domain.Remote.ModelLocation;
import com.kth.journal.domain.Remote.ModelObservation;
import com.kth.journal.domain.Remote.ModelOrganization;
import com.kth.journal.domain.Remote.ModelPatient;
import com.kth.journal.domain.Remote.ModelPractitioner;

import java.util.List;

public interface RemoteReposetoryServiceInterface {

    public List<ModelCondition> getConditions(Account patient, Account requster);

    public List<ModelEncounter> getEncounters(Account patient, Account requster);

    public List<ModelLocation> getLocations();

    public List<ModelObservation> getObservations(Account patient, Account requster);

    public List<ModelOrganization> getOrganizations();

    public List<ModelPatient> getPatients(String requsterEmail);

    public ModelPatient getPatient(String id, String requsterEmail);

    public ModelPractitioner getPractitioner(String email);

}