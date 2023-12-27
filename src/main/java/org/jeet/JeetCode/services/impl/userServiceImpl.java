package org.jeet.JeetCode.services.impl;

import java.util.Optional;

import org.jeet.JeetCode.domain.entities.SignUpRequest;
import org.jeet.JeetCode.domain.entities.UserEntity;
import org.jeet.JeetCode.repository.UserRepository;
import org.jeet.JeetCode.services.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl implements UserService , UserDetailsService
{
    private UserRepository userRepository;
    private PasswordEncoder bCryptPasswordEncoder;
    public userServiceImpl(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void signUpUser(SignUpRequest signUpRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(signUpRequest.getUserName());
        userEntity.setFullName(signUpRequest.getFullName());
        userEntity.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity findById(String userName) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userName);
        if(optionalUserEntity.isPresent()){
            UserEntity user = optionalUserEntity.get();
            return user;
        }
        return null;
    }

    /**Deprecated
    @Override
    public boolean CustomAuthenticate(LoginRequest loginRequest) {
        Optional<UserEntity> optionalUser = userRepository.findById(loginRequest.getUserName());
        if(optionalUser.isEmpty()) return false;
        UserEntity user = optionalUser.get();
        return bCryptPasswordEncoder.matches(loginRequest.getPassword(),user.getPassword());
    }
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<UserEntity> optionalUserEntity = userRepository.findById(username);
        if(optionalUserEntity.isEmpty())
            throw new UsernameNotFoundException("User not found with username: " + username);
        UserEntity userEntity = optionalUserEntity.get();
        UserDetails user = User.withUsername(userEntity.getUserName()).password(userEntity.getPassword()).build();
        return user;
    }

}
