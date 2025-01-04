package org.kth.app;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.kth.app.dto.SearchResponse;
import org.kth.app.service.PatientService;
import org.kth.app.service.PractitionerService;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@QuarkusTest
public class PractitionerControllerTest {
    @InjectMock
    PractitionerService practitionerService;

    private static final Long PRACTITIONER_ID = 1L;
    private static final Long ACCOUNT_ID = 1L;
    private static final String NAME = "John Doe";
    private static final String EMAIL = "john.doe@example.com";
    private static final String ROLE = "DOCTOR";

    @Test
    void searchByPartialName_Success() {
        // Arrange
        String searchName = "John";
        List<SearchResponse> responses = Arrays.asList(
                new SearchResponse(PRACTITIONER_ID, ACCOUNT_ID, NAME, EMAIL, ROLE),
                new SearchResponse(2L, 2L, "Johnny Smith", "johnny.smith@example.com", ROLE)
        );

        when(practitionerService.searchPractitionersByPartialName(searchName))
                .thenReturn(Multi.createFrom().items(responses.stream()));

        // Act & Assert
        given()
                .when()
                .get("/api/practitioner-search/by-name?name=" + searchName)
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("$.size()", is(2))
                .body("[0].name", equalTo(NAME))
                .body("[0].email", equalTo(EMAIL))
                .body("[0].role", equalTo(ROLE))
                .body("[1].name", equalTo("Johnny Smith"));

        verify(practitionerService).searchPractitionersByPartialName(searchName);
    }

    @Test
    void searchByPartialName_NoResults() {
        // Arrange
        String searchName = "XYZ";
        when(practitionerService.searchPractitionersByPartialName(searchName))
                .thenReturn(Multi.createFrom().empty());

        // Act & Assert
        given()
                .when()
                .get("/api/practitioner-search/by-name?name=" + searchName)
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON)
                .body("$.size()", is(0));

        verify(practitionerService).searchPractitionersByPartialName(searchName);
    }
}
