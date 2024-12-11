package org.kth.app.repository;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.kth.app.domain.Account;
import org.kth.app.domain.Practitioner;

@ApplicationScoped
public class PractitionerRepository {

    @Inject
    MySQLPool client; // Reactive MySQL client

    // Search for practitioner by partial name match
    public Multi<Practitioner> findByPartialName(String name) {
        String query = """
            SELECT p.* FROM practitioner p
            JOIN account a ON p.account_id = a.id
            WHERE LOWER(a.name) LIKE ?
        """;
        return client.preparedQuery(query)
                .execute(Tuple.of("%" + name.toLowerCase() + "%"))
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                .onItem().transform(this::mapRowToPractitioner);
    }

    // Helper method to map database rows to Practitioner entities
    private Practitioner mapRowToPractitioner(Row row) {
        Practitioner practitioner = new Practitioner();
        practitioner.setId(row.getLong("id")); // Practitioner ID

        // Map Account (only ID here, fetch full details as needed)
        if (row.getLong("account_id") != null) {
            Account account = new Account();
            account.setId(row.getLong("account_id"));
            practitioner.setAccount(account);
        }
        return practitioner;
    }
}

