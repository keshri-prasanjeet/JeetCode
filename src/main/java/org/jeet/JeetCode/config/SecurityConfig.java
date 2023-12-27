package org.jeet.JeetCode.config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeet.JeetCode.Utility.JwtTokenFilter;
import org.jeet.JeetCode.Utility.JwtUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    public SecurityConfig(UserDetailsService userDetailsService, JwtUtil jwtUtil){
        this.userDetailsService=userDetailsService;
        this.jwtUtil=jwtUtil;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests((authorize) ->
//                    authorize
//                            .requestMatchers("/index").permitAll()
//                            .requestMatchers("/hello").permitAll()
//                            .requestMatchers("/login/").permitAll()
//                            .requestMatchers("/problems").permitAll()
//                            .requestMatchers("/register/**").permitAll()
//                            .requestMatchers("/signup/**").permitAll()
//            );
//        return http.build();
//    }

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.disable())
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/signup").permitAll()
//                                .requestMatchers("/hello", "/login", "/problems", "/signup/**").permitAll()
//                                .anyRequest().authenticated()
//                )
//
//                .formLogin(formLogin ->
//                        formLogin
//                                .loginPage("/signup") // specify the login page URL
//                                .defaultSuccessUrl("/login") // specify the success URL after login
//                                .permitAll()
//                )
//                .logout(logout ->
//                        logout
//                                .permitAll()
//                );
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/signup", "/login", "/signup/**", "/hello", "/onlyTest", "/onlyTest/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/onlyTest").permitAll()
                        .requestMatchers(HttpMethod.POST, "/onlyTest/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler(this::loginSuccessHandler)
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(logout.getLogoutSuccessHandler())
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll())
                .addFilterAfter(new JwtTokenFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private void loginSuccessHandler(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException {
        String username = authentication.getName();
        String token = jwtUtil.generateToken(username);
        Cookie cookie = new Cookie("Authorization", "Bearer"+token);
        cookie.setMaxAge((int) TimeUnit.MILLISECONDS.toSeconds(jwtUtil.EXPIRATION_TIME));
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendRedirect("/problems");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)//telling it where to find user detials in our case, inside userServiceImpl.loadUserByUsername
                .passwordEncoder(passwordEncoder());
    }
}