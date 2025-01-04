package org.kth.app;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.kth.app.dto.SearchResponse;
import org.kth.app.service.PatientService;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@QuarkusTest
public class PatientControllerTest {
    @InjectMock
    PatientService patientService;

    private static final Long PATIENT_ID = 1L;
    private static final Long ACCOUNT_ID = 1L;
    private static final String NAME = "John Doe";
    private static final String EMAIL = "john.doe@example.com";
    private static final String ROLE = "PATIENT";

    @Test
    void searchByPartialName_Success() {
        // Arrange
        String searchName = "John";
        List<SearchResponse> responses = Arrays.asList(
                new SearchResponse(PATIENT_ID, ACCOUNT_ID, NAME, EMAIL, ROLE),
                new SearchResponse(2L, 2L, "Johnny Smith", "johnny.smith@example.com", ROLE)
        );

        when(patientService.searchPatientsByPartialName(searchName))
                .thenReturn(Multi.createFrom().items(responses.stream()));

        // Act & Assert
        given()
                .when()
                .get("/api/patient-search/by-name?name=" + searchName)
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("$.size()", is(2))
                .body("[0].name", equalTo(NAME))
                .body("[0].email", equalTo(EMAIL))
                .body("[0].role", equalTo(ROLE))
                .body("[1].name", equalTo("Johnny Smith"));

        verify(patientService).searchPatientsByPartialName(searchName);
    }

    @Test
    void searchByPartialName_NoResults() {
        // Arrange
        String searchName = "XYZ";
        when(patientService.searchPatientsByPartialName(searchName))
                .thenReturn(Multi.createFrom().empty());

        // Act & Assert
        given()
                .when()
                .get("/api/patient-search/by-name?name=" + searchName)
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("$.size()", is(0));

        verify(patientService).searchPatientsByPartialName(searchName);
    }

    @Test
    void searchByPartialEmail_Success() {
        // Arrange
        String searchEmail = "john";
        List<SearchResponse> responses = Arrays.asList(
                new SearchResponse(PATIENT_ID, ACCOUNT_ID, NAME, EMAIL, ROLE),
                new SearchResponse(2L, 2L, "Jane Johnson", "jane.johnson@example.com", ROLE)
        );

        when(patientService.searchPatientsByPartialEmail(searchEmail))
                .thenReturn(Multi.createFrom().items(responses.stream()));

        // Act & Assert
        given()
                .when()
                .get("/api/patient-search/by-email?email=" + searchEmail)
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("$.size()", is(2))
                .body("[0].email", equalTo(EMAIL))
                .body("[1].email", equalTo("jane.johnson@example.com"));

        verify(patientService).searchPatientsByPartialEmail(searchEmail);
    }

    @Test
    void searchByPartialCondition_Success() {
        // Arrange
        String searchCondition = "diabetes";
        List<SearchResponse> responses = Arrays.asList(
                new SearchResponse(PATIENT_ID, ACCOUNT_ID, NAME, EMAIL, ROLE),
                new SearchResponse(2L, 2L, "Jane Smith", "jane.smith@example.com", ROLE)
        );

        when(patientService.searchPatientsByPartialCondition(searchCondition))
                .thenReturn(Multi.createFrom().items(responses.stream()));

        // Act & Assert
        given()
                .when()
                .get("/api/patient-search/by-condition?condition=" + searchCondition)
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("$.size()", is(2))
                .body("[0].name", equalTo(NAME))
                .body("[1].name", equalTo("Jane Smith"));

        verify(patientService).searchPatientsByPartialCondition(searchCondition);
    }

    @Test
    void searchByPartialCondition_NoResults() {
        // Arrange
        String searchCondition = "nonexistent";
        when(patientService.searchPatientsByPartialCondition(searchCondition))
                .thenReturn(Multi.createFrom().empty());

        // Act & Assert
        given()
                .when()
                .get("/api/patient-search/by-condition?condition=" + searchCondition)
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("$.size()", is(0));

        verify(patientService).searchPatientsByPartialCondition(searchCondition);
    }

    @Test
    void searchByPartialName_ServiceError() {
        // Arrange
        String searchName = "John";
        when(patientService.searchPatientsByPartialName(searchName))
                .thenReturn(Multi.createFrom().failure(new RuntimeException("Service error")));

        // Act & Assert
        given()
                .when()
                .get("/api/patient-search/by-name?name=" + searchName)
                .then()
                .statusCode(500);

        verify(patientService).searchPatientsByPartialName(searchName);
    }

    @Test
    void searchByPartialName_EmptyParameter() {
        // Arrange
        String searchName = "";
        when(patientService.searchPatientsByPartialName(searchName))
                .thenReturn(Multi.createFrom().empty());

        // Act & Assert
        given()
                .when()
                .get("/api/patient-search/by-name?name=" + searchName)
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("$.size()", is(0));

        verify(patientService).searchPatientsByPartialName(searchName);
    }
}
