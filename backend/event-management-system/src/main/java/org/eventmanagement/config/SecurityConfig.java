package org.eventmanagement.config;

import org.eventmanagement.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.setAllowedOrigins(List.of("http://localhost:5173"));
                    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE","PATCH"));
                    corsConfig.setAllowCredentials(true);
                    corsConfig.setAllowedHeaders(List.of("*"));
                    return corsConfig;
                })).authorizeHttpRequests(request ->
                        request.requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/public/**", "/login/**", "/auth/checkAuth").permitAll()
                                .anyRequest().authenticated()
                ).formLogin(
                        form ->
                                form.loginProcessingUrl("/login/**")
                                        .successHandler((req, res, auth) -> {
                                            String path = req.getRequestURI();

                                            boolean isAdminRequest = path.contains("login/admin");
                                            boolean isUserRequest = path.contains("login/user");

                                            boolean isAdmin = auth.getAuthorities().stream()
                                                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

                                            boolean isUser = auth.getAuthorities().stream()
                                                    .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));

                                            if (isAdminRequest && !isAdmin) {
                                                res.setStatus(403);
                                                res.getWriter().write("Access Denied: Not Admin");
                                                return;
                                            }

                                            if (isUserRequest && !isUser) {
                                                res.setStatus(403);
                                                res.getWriter().write("Access Denied: Not User");
                                                return;
                                            }

                                            res.setStatus(200);

                                        })
                                        .failureHandler((req, res, ex) -> res.setStatus(401))
                ).logout(logout ->
                        logout.logoutUrl("/logout")
                                .logoutSuccessHandler((req, res, auth) -> res.setStatus(200))
                ).sessionManagement(session -> session.maximumSessions(1))
                .build();


    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
