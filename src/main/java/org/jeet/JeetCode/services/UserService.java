package org.jeet.JeetCode.services;

import org.jeet.JeetCode.domain.entities.LoginRequest;
import org.jeet.JeetCode.domain.entities.SignUpRequest;

public interface UserService {
    void signUpUser(SignUpRequest signUpRequest);

    boolean authenticate(LoginRequest loginRequest);
}
