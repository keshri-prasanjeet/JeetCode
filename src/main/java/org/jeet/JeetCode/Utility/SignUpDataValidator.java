package org.jeet.JeetCode.Utility;

import java.util.Objects;

import org.jeet.JeetCode.domain.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class SignUpDataValidator {
    public boolean checkIfSignUpPostDataIsValid(UserEntity user){
        return !Objects.equals(user.getUserName(), null)
                && !Objects.equals(user.getPassword(), null)
                && !Objects.equals(user.getFullName(), null);
    }
}
