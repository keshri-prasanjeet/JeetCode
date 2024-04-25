package org.jeet.JeetCode.services.impl;

import org.jeet.JeetCode.domain.entities.AdminDetail;
import org.jeet.JeetCode.domain.entities.UserEntity;
import org.jeet.JeetCode.repository.UserRepository;
import org.jeet.JeetCode.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class userServiceImpl implements UserService
{
    private UserRepository userRepository;
    private PasswordEncoder bCryptPasswordEncoder;

    public userServiceImpl(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void signUpUser(AdminDetail adminDetail) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(adminDetail.getFullName());
        userEntity.setEmailId(adminDetail.getEmailId());
        userEntity.setPassword(bCryptPasswordEncoder.encode(adminDetail.getPassword()));
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

    @Override
    public void updateSubmissionCount(String userName) {
        UserEntity user = findById(userName);
        Long curSubCou = user.getSubmissionCount();
        if(curSubCou == null) curSubCou = 0L;
        System.err.println(curSubCou);
        user.setSubmissionCount(curSubCou+1);
        userRepository.save(user);
    }

    /*Deprecated
    @Override
    public boolean CustomAuthenticate(LoginRequest loginRequest) {
        Optional<UserEntity> optionalUser = userRepository.findById(loginRequest.getUserName());
        if(optionalUser.isEmpty()) return false;
        UserEntity user = optionalUser.get();
        return bCryptPasswordEncoder.matches(loginRequest.getPassword(),user.getPassword());
    }
    */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        final Optional<UserEntity> optionalUserEntity = userRepository.findById(username);
//        if(optionalUserEntity.isEmpty())
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        UserEntity userEntity = optionalUserEntity.get();
//        // The UserDetails object is created with the retrieved username and hashed password
//        UserDetails user = User.withUsername(userEntity.getFullName())
//                               .password(userEntity.getPassword())  // <- This is where the hashed password is retrieved
//                               .build();
//        return user;
//    }

}
