package org.jeet.JeetCode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private UserDetailsService userDetailsService;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/signup","/hello", "/login", "/signup/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll())
                .formLogin((form) -> form.defaultSuccessUrl("/hello", true));

        return http.build();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}