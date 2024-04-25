package org.jeet.JeetCode.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.jeet.JeetCode.Utility.JwtUtil;
import org.jeet.JeetCode.domain.dto.AuthenticationResponse;
import org.jeet.JeetCode.domain.entities.AdminDetail;
import org.jeet.JeetCode.services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@RestController
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AdminDetail adminDetail, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        System.out.println("Enterred the login controller");
        System.out.println(adminDetail.getFullName()+ adminDetail.getPassword());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(adminDetail.getFullName(), adminDetail.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return null;
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(adminDetail.getFullName());

        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return new AuthenticationResponse(jwt);

    }
}