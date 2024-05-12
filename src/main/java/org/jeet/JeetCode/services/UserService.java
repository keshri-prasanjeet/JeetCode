package org.jeet.JeetCode.services;

import org.jeet.JeetCode.domain.entities.AdminDetail;
import org.jeet.JeetCode.domain.entities.UserEntity;

public interface UserService {
    void signUpUser(AdminDetail adminDetail);

    UserEntity findById(String userName);

    void updateSubmissionCount(String userName);

    //boolean CustomAuthenticate(LoginRequest loginRequest);
}
