package org.jeet.JeetCode.services.impl;

import org.jeet.JeetCode.domain.entities.UserEntity;
import org.jeet.JeetCode.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Optional<UserEntity> optionalUserEntity = userRepository.findById(username);
        if(optionalUserEntity.isEmpty())
            throw new UsernameNotFoundException("User not found with username: " + username);
        UserEntity userEntity = optionalUserEntity.get();

        return new org.springframework.security.core.userdetails.User(userEntity.getFullName(), userEntity.getPassword(), new ArrayList<>());
    }
}
