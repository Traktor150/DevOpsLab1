package com.kth.journal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.kth.journal.Controlers.UserController;
import com.kth.journal.Dto.RecipientResponse;
import com.kth.journal.Services.UserService;
import com.kth.journal.domain.Account;
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
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private Jwt jwt;

    private static final String TEST_EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(jwt);
        when(jwt.getClaimAsString("email")).thenReturn(TEST_EMAIL);
    }

    @Test
    void getRecipients_Success() {
        // Arrange
        List<Account> accounts = Arrays.asList(
                createTestAccount(1L, "Doctor Smith", "DOCTOR"),
                createTestAccount(2L, "Nurse Johnson", "STAFF"),
                createTestAccount(3L, "Patient Brown", "PATIENT")
        );

        when(userService.getRecipients(TEST_EMAIL)).thenReturn(accounts);

        // Act
        ResponseEntity<List<RecipientResponse>> response = userController.getRecipients();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(3);

        RecipientResponse firstRecipient = response.getBody().get(0);
        assertThat(firstRecipient.id()).isEqualTo(1L);
        assertThat(firstRecipient.name()).isEqualTo("Doctor Smith");
        assertThat(firstRecipient.role()).isEqualTo("DOCTOR");

        verify(userService).getRecipients(TEST_EMAIL);
    }

    @Test
    void getRecipients_EmptyList() {
        // Arrange
        when(userService.getRecipients(TEST_EMAIL)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<RecipientResponse>> response = userController.getRecipients();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(0);
        verify(userService).getRecipients(TEST_EMAIL);
    }

    @Test
    void getRecipients_ServiceThrowsException() {
        // Arrange
        when(userService.getRecipients(TEST_EMAIL))
                .thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userController.getRecipients());
        verify(userService).getRecipients(TEST_EMAIL);
    }

    private Account createTestAccount(Long id, String name, String role) {
        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setEmail(name.toLowerCase().replace(" ", ".") + "@example.com");
        account.setRole(role);
        return account;
    }
}