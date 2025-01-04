package com.kth.journal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.kth.journal.Controlers.ConditionController;
import com.kth.journal.Dto.ConditionRequest;
import com.kth.journal.Services.ConditionService;
import com.kth.journal.Services.UserService;
import com.kth.journal.domain.Account;
import com.kth.journal.domain.MedicalCondition;
import com.kth.journal.domain.Practitioner;
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
class ConditionControllerTest {

    @Mock
    private ConditionService conditionService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ConditionController conditionController;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private Jwt jwt;

    private static final String TEST_EMAIL = "doctor@example.com";
    private static final Long PATIENT_ID = 1L;
    private static final Long PRACTITIONER_ID = 1L;
    private static final String DIAGNOSIS_NAME = "Hypertension";
    private static final String DIAGNOSIS_DESC = "High blood pressure condition";

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(jwt);
        when(jwt.getClaimAsString("email")).thenReturn(TEST_EMAIL);
    }

    @Test
    void createCondition_Success() {
        // Arrange
        ConditionRequest request = createConditionRequest();
        Account practitionerAccount = createPractitionerAccount();
        MedicalCondition condition = createMedicalCondition();

        when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(practitionerAccount));
        when(conditionService.createCondition(
                PATIENT_ID,
                PRACTITIONER_ID,
                DIAGNOSIS_NAME,
                DIAGNOSIS_DESC
        )).thenReturn(condition);

        // Act
        ResponseEntity<String> response = conditionController.createNote(request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Condition created successfully");
        verify(conditionService).createCondition(
                PATIENT_ID,
                PRACTITIONER_ID,
                DIAGNOSIS_NAME,
                DIAGNOSIS_DESC
        );
    }

    @Test
    void createCondition_UserNotFound() {
        // Arrange
        ConditionRequest request = createConditionRequest();
        when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = conditionController.createNote(request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        verify(conditionService, never()).createCondition(any(), any(), any(), any());
    }

    @Test
    void createCondition_NotPractitioner() {
        // Arrange
        ConditionRequest request = createConditionRequest();
        Account nonPractitionerAccount = new Account();
        nonPractitionerAccount.setId(1L);
        nonPractitionerAccount.setEmail(TEST_EMAIL);
        nonPractitionerAccount.setRole("PATIENT");

        when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(nonPractitionerAccount));

        // Act
        ResponseEntity<String> response = conditionController.createNote(request);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        verify(conditionService, never()).createCondition(any(), any(), any(), any());
    }

    @Test
    void createCondition_ServiceThrowsException() {
        // Arrange
        ConditionRequest request = createConditionRequest();
        Account practitionerAccount = createPractitionerAccount();

        when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(Optional.of(practitionerAccount));
        when(conditionService.createCondition(
                PATIENT_ID,
                PRACTITIONER_ID,
                DIAGNOSIS_NAME,
                DIAGNOSIS_DESC
        )).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> conditionController.createNote(request));
    }

    private ConditionRequest createConditionRequest() {
        ConditionRequest request = new ConditionRequest();
        request.setPatientId(PATIENT_ID);
        request.setDiagnosisName(DIAGNOSIS_NAME);
        request.setDiagnosisDesc(DIAGNOSIS_DESC);
        return request;
    }

    private Account createPractitionerAccount() {
        Practitioner practitioner = new Practitioner();
        practitioner.setId(PRACTITIONER_ID);

        Account account = new Account();
        account.setId(1L);
        account.setEmail(TEST_EMAIL);
        account.setRole("DOCTOR");
        account.setPractitioner(practitioner);

        return account;
    }

    private MedicalCondition createMedicalCondition() {
        MedicalCondition condition = new MedicalCondition();
        condition.setId(1L);
        condition.setName(DIAGNOSIS_NAME);
        condition.setDescription(DIAGNOSIS_DESC);
        condition.setDiagnosedAt(new Date());
        return condition;
    }
}