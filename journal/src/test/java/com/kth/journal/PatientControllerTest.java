package com.kth.journal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

import com.kth.journal.Controlers.PatientController;
import com.kth.journal.Dto.PatientProfile;
import com.kth.journal.Dto.PatientResponse;
import com.kth.journal.Services.PatientService;
import com.kth.journal.Services.UserService;
import com.kth.journal.domain.Account;
import com.kth.journal.domain.Patient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {
    @Mock
    private PatientService patientService;

    @Mock
    private UserService userService;

    @InjectMocks
    private PatientController patientController;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private Jwt jwt;

    private static final String TEST_EMAIL = "test@example.com";
    private static final Long ACCOUNT_ID = 1L;
    private static final Long PATIENT_ID = 1L;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(jwt);
        when(jwt.getClaimAsString("email")).thenReturn(TEST_EMAIL);
    }

    @Test
    void getPatientInfo_Success() {
        // Arrange
        Account account = createTestAccount("PATIENT");
        Patient patient = createTestPatient(account);

        when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(account));
        when(patientService.getPatientByAccountId(ACCOUNT_ID)).thenReturn(patient);

        // Act
        ResponseEntity<PatientProfile> response = patientController.getPatientInfo();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(PATIENT_ID);
        assertThat(response.getBody().getAccount().email()).isEqualTo(TEST_EMAIL);
        verify(userService).getUserByEmail(TEST_EMAIL);
        verify(patientService).getPatientByAccountId(ACCOUNT_ID);
    }

    @Test
    void getPatientInfo_UserNotFound() {
        // Arrange
        when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<PatientProfile> response = patientController.getPatientInfo();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void getAllPatients_Success() {
        // Arrange
        Account doctorAccount = createTestAccount("DOCTOR");
        List<Patient> patients = Arrays.asList(
                createTestPatient(createTestAccount("PATIENT")),
                createTestPatient(createTestAccount("PATIENT"))
        );

        when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(doctorAccount));
        when(patientService.getAllPatients()).thenReturn(patients);

        // Act
        ResponseEntity<List<PatientResponse>> response = patientController.getAllPatients();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(2);
        verify(patientService).getAllPatients();
    }

    @Test
    void getAllPatients_UnauthorizedForPatient() {
        // Arrange
        Account patientAccount = createTestAccount("PATIENT");
        when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(patientAccount));

        // Act
        ResponseEntity<List<PatientResponse>> response = patientController.getAllPatients();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
        verify(patientService, never()).getAllPatients();
    }

    @Test
    void getPatientById_Success() {
        // Arrange
        Account doctorAccount = createTestAccount("DOCTOR");
        Patient patient = createTestPatient(createTestAccount("PATIENT"));

        when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(doctorAccount));
        when(patientService.getPatientByAccountId(ACCOUNT_ID)).thenReturn(patient);

        // Act
        ResponseEntity<PatientProfile> response = patientController.getPatientById(ACCOUNT_ID);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(PATIENT_ID);
        verify(patientService).getPatientByAccountId(ACCOUNT_ID);
    }

    @Test
    void getPatientById_UnauthorizedForNonDoctor() {
        // Arrange
        Account staffAccount = createTestAccount("STAFF");
        when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(staffAccount));

        // Act
        ResponseEntity<PatientProfile> response = patientController.getPatientById(ACCOUNT_ID);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
        verify(patientService, never()).getPatientByAccountId(any());
    }

    private Account createTestAccount(String role) {
        Account account = new Account();
        account.setId(ACCOUNT_ID);
        account.setEmail(TEST_EMAIL);
        account.setName("Test User");
        account.setRole(role);
        return account;
    }

    private Patient createTestPatient(Account account) {
        Patient patient = new Patient();
        patient.setId(PATIENT_ID);
        patient.setAccount(account);
        patient.setConditions(new ArrayList<>());
        patient.setNotes(new ArrayList<>());
        return patient;
    }
}
