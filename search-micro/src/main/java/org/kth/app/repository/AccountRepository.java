package org.kth.app.repository;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.kth.app.domain.Account;

@ApplicationScoped
public class AccountRepository {
    @Inject
    io.vertx.mutiny.mysqlclient.MySQLPool client;

    public Uni<Account> findById(Long id) {
        String query = "SELECT * FROM account WHERE id = ?";
        return client.preparedQuery(query)
                .execute(Tuple.of(id))
                .onItem().transform(set -> {
                    if (set.size() == 0) {
                        return null;
                    }
                    return mapRowToAccount(set.iterator().next());
                });
    }

    private Account mapRowToAccount(Row row) {
        Account account = new Account();
        account.setId(row.getLong("id"));
        account.setName(row.getString("name"));
        account.setEmail(row.getString("email"));
        account.setRole(row.getString("role"));
        return account;
    }
}

