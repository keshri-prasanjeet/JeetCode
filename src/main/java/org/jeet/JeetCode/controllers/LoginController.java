//package org.jeet.JeetCode.controllers;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.jeet.JeetCode.Utility.JwtUtilNew;
//import org.jeet.JeetCode.domain.entities.AdminDetail;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class LoginController {
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtilNew jwtUtil;
//
//    public LoginController(AuthenticationManager authenticationManager, JwtUtilNew jwtUtil) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtil = jwtUtil;
//    }
//    @CrossOrigin("http://localhost:4200")
//    @PostMapping("/Oldlogin")
//    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody AdminDetail adminDetail) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(adminDetail.getFullName(), adminDetail.getPassword())
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            String token = jwtUtil.generateToken(adminDetail.getFullName());
//            // Return the token as a JSON object
//            Map<String, String> response = new HashMap<>();
//            response.put("token", token);
//            return new ResponseEntity( response,HttpStatus.OK);
//        } catch (
//                Exception e) {
//            // Handle authentication failure
//            return ResponseEntity.status(401).body(Collections.singletonMap("error", "Authentication failed"));
//        }
//    }
//}