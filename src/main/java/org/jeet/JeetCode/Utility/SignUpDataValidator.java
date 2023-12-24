package org.jeet.JeetCode.Utility;

import java.util.Objects;

import org.jeet.JeetCode.domain.entities.SignUpRequest;
import org.jeet.JeetCode.domain.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class SignUpDataValidator {
    public boolean checkIfSignUpPostDataIsValid(SignUpRequest signUpRequest){
        return !Objects.equals(signUpRequest.getUserName(), null)
                && !Objects.equals(signUpRequest.getPassword(), null)
                && !Objects.equals(signUpRequest.getFullName(), null);
    }
}
