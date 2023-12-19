package org.jeet.JeetCode.services;

import org.jeet.JeetCode.domain.entities.LoginRequest;
import org.jeet.JeetCode.domain.entities.UserEntity;

public interface UserService {
    void signUpUser(UserEntity userEntity);

    boolean authenticate(LoginRequest loginRequest);
}
