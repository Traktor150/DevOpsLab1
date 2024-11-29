package com.kth.auth.Controlers;

import com.kth.auth.Dto.*;
import com.kth.auth.Security.SecurityConfig;
import com.kth.auth.Services.PatientService;
import com.kth.auth.Services.PractitionerService;
import com.kth.auth.Services.UserService;
import com.kth.auth.Utils.JwtUtil;
import com.kth.auth.domain.Account;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PatientService patientService;
    private final PractitionerService practitionerService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<Account> userOptional = userService.validUsernameAndPassword(email, password);

        if (userOptional.isPresent()) {
            Account user = userOptional.get();
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(new AuthResponse(user.getId(), user.getEmail(), user.getName(), user.getRole(), token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequestPatient signUpRequest) throws Exception {
        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            throw new Exception(String.format("Email %s is already been used", signUpRequest.getEmail()));
        }

        Account user = userService.saveUser(createUser(signUpRequest, SecurityConfig.PATIENT));

        patientService.createPatient(user.getId());

        return ResponseEntity.ok("User registered successfully");
    }

    private Account createUser(SignUpRequestPatient signUpRequest, String role) {
        Account user = new Account();
        user.setPassword(signUpRequest.getPassword());
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(role);
        return user;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup/staff")
    public ResponseEntity<String> signUpStaff(@Valid @RequestBody SignUpRequest signUpRequest) throws Exception {
        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            throw new Exception(String.format("Email %s is already been used", signUpRequest.getEmail()));
        }

        Account user = userService.saveUser(createUser(signUpRequest, SecurityConfig.STAFF));

        practitionerService.createPractitioner(user.getId());

        return ResponseEntity.ok("User registered successfully");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup/doctor")
    public ResponseEntity<String> signUpDoctor(@Valid @RequestBody SignUpRequest signUpRequest) throws Exception {
        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            throw new Exception(String.format("Email %s is already been used", signUpRequest.getEmail()));
        }

        Account user = userService.saveUser(createUser(signUpRequest, SecurityConfig.DOCTOR));

        practitionerService.createPractitioner(user.getId());

        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/byEmail")
    public UserByEmailResponse gerUserByEmail(@RequestParam String email) {
        Optional<Account> isUser = userService.getUserByEmail(email);
        if(isUser.isPresent()) {
            Account user = isUser.get();
            return new UserByEmailResponse(user.getId(), user.getPassword(), user.getName(), user.getEmail(), user.getRole());
        }
        return null;
    }

    private Account createUser(SignUpRequest signUpRequest, String role) {
        Account user = new Account();
        user.setPassword(signUpRequest.getPassword());
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(role);
        return user;
    }
}
