package org.jeet.JeetCode.Utility;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeet.JeetCode.services.impl.userServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private userServiceImpl userService;
    private final UserDetailsService userDetailsService;
    public JwtTokenFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService=userDetailsService;
    }

    private String extractToken(HttpServletRequest request) {
        // Extract token from request, e.g., from Authorization header or cookie
        // Implement according to your token extraction logic
        // For example, you can use request.getHeader("Authorization") or request.getCookies()
        // Ensure to handle multiple scenarios based on your application's requirements
        String header = request.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer")){
            return header.substring(7);
        }
        return null;
    }

    private Authentication createAuthentication(String username) {
        // Implement creating an Authentication object based on the extracted username
        // You may need to implement your UserDetails and UserDetailsService
        // For example, you can use JwtAuthenticationToken
        UserDetails user = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    jakarta.servlet.FilterChain filterChain) throws ServletException, IOException {
        String token;
        token = extractToken(request);
        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUserName(token);
            // Create Authentication object and set it to SecurityContextHolder
            // You may need to implement your UserDetails and UserDetailsService
            Authentication authentication = createAuthentication(username);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
