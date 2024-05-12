//package org.jeet.JeetCode.Utility;
//
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.jeet.JeetCode.services.impl.userServiceImpl;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//    private userServiceImpl userService;
//    private final UserDetailsService userDetailsService;
//    public JwtTokenFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
//        this.jwtUtil = jwtUtil;
//        this.userDetailsService=userDetailsService;
//    }
//
//    private String extractToken(HttpServletRequest request) {
//        // Extract token from request, e.g., from Authorization header or cookie
//        // Implement according to your token extraction logic
//        // For example, you can use request.getHeader("Authorization") or request.getCookies()
//        // Ensure to handle multiple scenarios based on your application's requirements
//        String header = request.getHeader("Authorization");
//        if(header!=null && header.startsWith("Bearer")){
//            return header.substring(7);
//        }
//        System.out.println("JwtTokenFilter: Token not found in Authorization header");
//        return null;
//    }
//
//    private Authentication createAuthentication(String username) {
//        // Implement creating an Authentication object based on the extracted username
//        // You may need to implement your UserDetails and UserDetailsService
//        // For example, you can use JwtAuthenticationToken
//        UserDetails user = userDetailsService.loadUserByUsername(username);
//        System.err.println("we are in the Authentication function of JwtTF");
//        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        try {
//            String token = extractToken(request);
//            System.out.println("request is " + request);
//            System.out.println("token is " + token);
//            System.out.println("JwtTokenFilter: Entering doFilterInternal");
//            if (token != null && jwtUtil.validateToken(token)) {
//                String username = jwtUtil.extractUserName(token);
//                // Create Authentication object and set it to SecurityContextHolder
//                Authentication authentication = createAuthentication(username);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                System.err.println("we are in doFilterInternal");
//                handleSuccessfulAuthentication(request, response, authentication);
//            }
//            else if(token==null){
//                System.out.println("token was null");
//            }
//            else{
//                System.out.println("token was invalid as per validateToken()");
//            }
//        } catch (Exception e) {
//            // Handle any exceptions that might occur during token processing
//            // Log or handle the error based on your application's requirements
//            // For example, you could send a 401 Unauthorized response
//            logger.error("Error processing JWT token", e);
//            System.err.println("JwtTokenFilter: Error processing JWT token - " + e.getMessage());
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Unauthorized");
//            return;
//        }
//
//        filterChain.doFilter(request, response);
//    }
//    private void handleSuccessfulAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException {
//        System.out.println("enterred the loginSuccesHandler");
//        String username = authentication.getName();
//        String token = jwtUtil.generateToken(username);
//        Cookie cookie = new Cookie("Authorization", "Bearer "+token);
//        cookie.setMaxAge((int) TimeUnit.MILLISECONDS.toSeconds(jwtUtil.EXPIRATION_TIME));
//        cookie.setHttpOnly(false);
//        cookie.setSecure(false);
//        cookie.setPath("/");
//        response.addCookie(cookie);
////        response.sendRedirect("/problems");
//    }
//}
