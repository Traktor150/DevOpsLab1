package com.kth.auth.Services.Interfaces;

import com.kth.auth.domain.Practitioner;

public interface PractitionerServiceInterface {
    Practitioner createPractitioner(Long accountId);
}
