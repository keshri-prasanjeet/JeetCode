package org.jeet.JeetCode.services.impl;

import java.util.Optional;

import javax.swing.text.html.Option;

import org.jeet.JeetCode.domain.entities.LoginRequest;
import org.jeet.JeetCode.domain.entities.UserEntity;
import org.jeet.JeetCode.repository.UserRepository;
import org.jeet.JeetCode.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public userServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void signUpUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @Override
    public boolean authenticate(LoginRequest loginRequest) {
        Optional<UserEntity> optionalUser = userRepository.findById(loginRequest.getUserName());
        if(optionalUser==null) return false;
        UserEntity user = optionalUser.get();
        return bCryptPasswordEncoder.matches(loginRequest.getPassword(),user.getPassword());
    }
}
