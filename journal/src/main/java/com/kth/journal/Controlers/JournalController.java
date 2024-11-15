package com.kth.journal.Controlers;

import java.util.List;

import javax.swing.Spring;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kth.journal.Dto.PatientResponse;
import com.kth.journal.Dto.PatientsResponse;
import com.kth.journal.Services.Interfaces.RemoteReposetoryServiceInterface;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/journal")
public class JournalController {
    private final RemoteReposetoryServiceInterface journalService;

    @GetMapping("/patients")
    public PatientsResponse getPatients() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {

            String userEmail = ((UserDetails) principal).getUsername();

            PatientsResponse res = new PatientsResponse(journalService.getPatients(userEmail));

            return res;
        } else

        {
            throw new RuntimeException("User is not authenticated");
        }
    }

    @GetMapping("/patient/{id}")
    public PatientResponse getPatient(@PathVariable String id) {
        // Object principal =
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // if (principal instanceof UserDetails) {

        // String userEmail = ((UserDetails) principal).getUsername();

        return new PatientResponse(journalService.getPatient(id));

    }
}
