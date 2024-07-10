package org.example.likelion.config.security;


import org.example.likelion.config.security.jwtConfig.AuthEntryPointJwt;
import org.example.likelion.config.security.jwtConfig.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.example.likelion.dto.auth.Role.ADMIN;
import static org.example.likelion.dto.auth.Role.USER;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/api/v1/file/upload/**",
            "/api/v1/ai-recommendation/**",
            "/api/v1/admin/**",

    };

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST_URL).permitAll()
                        // product
                        .requestMatchers(GET, "/api/v1/product/**").permitAll()
                        .requestMatchers(POST, "/api/v1/product/**").hasRole(ADMIN.name())
                        .requestMatchers(PUT, "/api/v1/product/**").hasRole(ADMIN.name())
                        .requestMatchers(DELETE, "/api/v1/product/**").hasRole(ADMIN.name())
                        //category
                        .requestMatchers(GET, "/api/v1/category/**").permitAll()
                        .requestMatchers(POST, "/api/v1/category/**").hasRole(ADMIN.name())
                        .requestMatchers(PUT, "/api/v1/category/**").hasRole(ADMIN.name())
                        .requestMatchers(DELETE, "/api/v1/category/**").hasRole(ADMIN.name())

                        .requestMatchers("/api/v1/voucher/**").permitAll()
                        .requestMatchers("/api/v1/otp/**").permitAll()
                        .requestMatchers("/api/v1/user/**").authenticated()
                        .requestMatchers("/ws/**").permitAll()
                        //order
                        .requestMatchers( "/api/v1/order/**").authenticated()
                        //order detail
                        .requestMatchers( "/api/v1/order-detail/**").authenticated()
                        //rom chat - admin
                        .requestMatchers(GET, "/api/v1/roomChat/admin/**").hasRole(ADMIN.name())
                        .requestMatchers(POST, "/api/v1/roomChat/admin/**").hasRole(ADMIN.name())
                        .requestMatchers(PUT, "/api/v1/roomChat/admin/**").hasRole(ADMIN.name())
                        .requestMatchers(DELETE, "/api/v1/roomChat/admin/**").hasRole(ADMIN.name())
                        // room chat - user
                        .requestMatchers(GET, "/api/v1/roomChat/user/**").hasRole(USER.name())
                        .requestMatchers(POST, "/api/v1/roomChat/user/**").hasRole(USER.name())
                        .requestMatchers(PUT, "/api/v1/roomChat/user/**").hasRole(USER.name())
                        .requestMatchers(DELETE, "/api/v1/roomChat/user/**").hasRole(USER.name())
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(ex -> {
                    ex.authenticationEntryPoint(unauthorizedHandler);
                    ex.accessDeniedHandler((request, response, authException) -> response.sendError(403, "Forbidden"));
                })
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
