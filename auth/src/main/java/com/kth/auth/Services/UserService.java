package com.kth.auth.Services;

import com.kth.auth.Repository.AccountRepository;
import com.kth.auth.Services.Interfaces.UserServiceInterface;
import com.kth.auth.domain.Account;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService implements UserServiceInterface {
    private final AccountRepository accRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${keycloak.server.url}")
    private String KEYCLOAK_SERVER_URL; // Keycloak base URL
    private final String REALM = "journal";
    private final String CLIENT_ID = "admin-cli";
    private final String ADMIN_USERNAME = "admin"; // Keycloak admin username
    private final String ADMIN_PASSWORD = "admin"; // Keycloak admin password

    private Keycloak getKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(KEYCLOAK_SERVER_URL)
                .realm("master")
                .clientId(CLIENT_ID)
                .username(ADMIN_USERNAME)
                .password(ADMIN_PASSWORD)
                .build();
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return accRepository.existsByEmail(email);
    }

    @Override
    public Account saveUser(Account user) {
        System.out.println("IN");
        Keycloak keycloak = getKeycloakInstance();

        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setUsername(user.getEmail());
        keycloakUser.setEmail(user.getEmail());
        keycloakUser.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(user.getPassword());
        credential.setTemporary(false);

        keycloakUser.setCredentials(Arrays.asList(credential));

        keycloak.realm(REALM).users().create(keycloakUser);
        System.out.println("OUT");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return accRepository.save(user);
    }

    @Override
    public Optional<Account> getUserByEmail(String email) {
        return accRepository.findByEmail(email);
    }

    @Override
    public Optional<Account> validUsernameAndPassword(String email, String password) {
        return getUserByEmail(email).filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }
}
