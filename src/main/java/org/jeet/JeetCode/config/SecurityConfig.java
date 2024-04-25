//package org.jeet.JeetCode.config;
//
//import java.util.Arrays;
//
//import org.jeet.JeetCode.Utility.JwtTokenFilter;
//import org.jeet.JeetCode.Utility.JwtUtil;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
//    private final UserDetailsService userDetailsService;
//
//    private final JwtUtil jwtUtil;
//
//    public SecurityConfig(UserDetailsService userDetailsService, JwtUtil jwtUtil){
//        this.userDetailsService=userDetailsService;
//        this.jwtUtil=jwtUtil;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public static PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http) throws Exception {
//        System.out.println("Entering securityFilterChain method");
//        http
//        .cors((cors) ->
//                cors.configurationSource(corsConfigurationSource()))
//        .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/signup", "/login", "/signup/**", "/problems/submissions/**", "/hello", "/login/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//            .logout((logout) -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessHandler(logout.getLogoutSuccessHandler())
//                        .invalidateHttpSession(true)
//                        .clearAuthentication(true)
//                        .permitAll())
//            .addFilterAfter(new JwtTokenFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource(){
//        System.out.println("this was enterred though");
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT","OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
//        configuration.setAllowCredentials(true); // Allow credentials for CORS requests
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
////        source.registerCorsConfiguration("/signup/**",configuration);
//        return source;
//    }
//
//    /**
//    The configureGlobal method is responsible for configuring the AuthenticationManagerBuilder,
//     specifying the userDetailsService and the password encoder to be used during the authentication process.
//     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)//telling it where to find user details in our case, inside userServiceImpl.loadUserByUsername
//                .passwordEncoder(passwordEncoder());
//    }
//
//}