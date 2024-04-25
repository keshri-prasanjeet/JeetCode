package org.jeet.JeetCode.controllers;

import org.jeet.JeetCode.domain.entities.AdminDetail;
import org.jeet.JeetCode.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<?> loginFromAngular(@RequestBody @NotNull AdminDetail adminDetail) {
        try {
            // Your registration logic here
            System.out.println(adminDetail);
            userService.signUpUser(adminDetail);
            return new ResponseEntity<>("{\"message\": \"Registration successful\"}", HttpStatus.CREATED);
        } catch (
                Exception e) {
            return new ResponseEntity<>("{\"error\": \"" + e.getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
