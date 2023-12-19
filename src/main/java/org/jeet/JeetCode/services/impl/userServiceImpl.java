package org.jeet.JeetCode.services.impl;

import org.jeet.JeetCode.domain.entities.UserEntity;
import org.jeet.JeetCode.repository.UserRepository;
import org.jeet.JeetCode.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl implements UserService {

    private UserRepository userRepository;

    public userServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void signUpUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
}
