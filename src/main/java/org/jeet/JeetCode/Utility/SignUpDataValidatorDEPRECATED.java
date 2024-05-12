package org.jeet.JeetCode.Utility;

import java.util.Objects;

import org.jeet.JeetCode.domain.entities.SignUpRequest;
import org.springframework.stereotype.Component;

@Component
public class SignUpDataValidatorDEPRECATED {
    public boolean checkIfSignUpPostDataIsValid(SignUpRequest signUpRequest){
        return !Objects.equals(signUpRequest.getUserName(), null)
                && !Objects.equals(signUpRequest.getPassword(), null)
                && !Objects.equals(signUpRequest.getFullName(), null);
    }
}
