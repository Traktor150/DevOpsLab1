package org.kth.app.repository;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.kth.app.domain.Account;
import org.kth.app.domain.Patient;

import java.util.List;

@ApplicationScoped
public class PatientRepository {

    @Inject
    MySQLPool client; // Reactive MySQL client

    // Search for patients by partial name match
    public Multi<Patient> findByPartialName(String name) {
        String query = """
        SELECT p.* FROM patient p
        JOIN account a ON p.account_id = a.id
        WHERE LOWER(a.name) LIKE ?
    """;

        return client.preparedQuery(query)
                .execute(Tuple.of("%" + name.toLowerCase() + "%"))
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                .onItem().transform(this::mapRowToPatient); // Map rows to Patient objects
    }

    // Search for patients by partial email match
    public Multi<Patient> findByPartialEmail(String email) {
        String query = """
        SELECT p.* FROM patient p
        JOIN account a ON p.account_id = a.id
        WHERE LOWER(a.email) LIKE ?
        """;
        return client.preparedQuery(query)
                .execute(Tuple.of("%" + email.toLowerCase() + "%"))
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                .onItem().transform(this::mapRowToPatient); // Map rows to Patient objects
    }

    // Search for patients by partial condition name match
    public Multi<Patient> findByPartialCondition(String conditionName) {
        String query = """
            SELECT * FROM patient p
            WHERE EXISTS (
                SELECT 1 FROM medical_condition mc
                WHERE mc.patient_id = p.id AND LOWER(mc.name) LIKE ?
            )
        """;

        return client.preparedQuery(query)
                .execute(Tuple.of("%" + conditionName.toLowerCase() + "%"))
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                .onItem().transform(this::mapRowToPatient); // Map rows to Patient objects
    }

    private Patient mapRowToPatient(Row row) {
        Patient patient = new Patient();
        patient.setId(row.getLong("id")); // Patient ID

        Account account = new Account();
        account.setId(row.getLong("account_id"));
        patient.setAccount(account);

        return patient;
    }
}

