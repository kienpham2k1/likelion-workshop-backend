package org.example.likelion.config.security;


import org.example.likelion.config.security.jwtConfig.AuthEntryPointJwt;
import org.example.likelion.config.security.jwtConfig.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

import static org.example.likelion.dto.auth.Permission.*;
import static org.example.likelion.dto.auth.Role.*;
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

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};

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
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST_URL)
                        .permitAll()
//                        .requestMatchers("/api/v1/product/**").hasAnyRole(ADMIN.name())
//                        .requestMatchers(
//                                new AntPathRequestMatcher("/api/v1/product/**", HttpMethod.POST.toString()),
//                                new AntPathRequestMatcher("/api/v1/product/**", HttpMethod.PUT.toString()),
//                                new AntPathRequestMatcher("/api/v1/product/**", HttpMethod.DELETE.toString())
//                        ).hasRole(ADMIN.name())
                        .requestMatchers(
                                request -> {
                                    return request.getMethod().equals(HttpMethod.POST.toString()) ||
                                            request.getMethod().equals(HttpMethod.PUT.toString()) ||
                                            request.getMethod().equals(HttpMethod.DELETE.toString());
                                },
                                new AntPathRequestMatcher("/api/v1/product/**")
                        ).hasRole(ADMIN.name())
                        .requestMatchers(
                                request -> {
                                    return request.getMethod().equals(HttpMethod.POST.toString()) ||
                                            request.getMethod().equals(HttpMethod.PUT.toString()) ||
                                            request.getMethod().equals(HttpMethod.DELETE.toString());
                                },
                                new AntPathRequestMatcher("/api/v1/category/**")
                        ).hasRole(ADMIN.name())
                        .requestMatchers(GET, "/api/v1/product/**").hasAnyAuthority(USER_READ.name())
                        .requestMatchers(
                                request -> {
                                    return request.getMethod().equals(POST.toString()) ||
                                            request.getMethod().equals(PUT.toString()) ||
                                            request.getMethod().equals(DELETE.toString());
                                },
                                new AntPathRequestMatcher("/api/v1/order/**")
                        ).authenticated()
                        .requestMatchers(
                                request -> {
                                    return request.getMethod().equals(GET.toString()) ||
                                            request.getMethod().equals(PUT.toString()) ||
                                            request.getMethod().equals(PUT.toString()) ||
                                            request.getMethod().equals(DELETE.toString());
                                },
                                new AntPathRequestMatcher("/api/v1/order-detail/**")
                        ).authenticated()
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}