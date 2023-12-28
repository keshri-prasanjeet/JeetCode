package org.jeet.JeetCode.services;

import org.jeet.JeetCode.domain.entities.SignUpRequest;
import org.jeet.JeetCode.domain.entities.UserEntity;

public interface UserService {
    void signUpUser(SignUpRequest signUpRequest);

    UserEntity findById(String userName);

    void updateSubmissionCount(String userName);

    //boolean CustomAuthenticate(LoginRequest loginRequest);
}
