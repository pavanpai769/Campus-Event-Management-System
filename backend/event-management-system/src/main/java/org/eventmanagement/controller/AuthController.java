package org.eventmanagement.controller;

import org.eventmanagement.dto.UserResponseDtoForUser;
import org.eventmanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDtoForUser> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserResponseDtoForUser user = authService.getCurrentUser(authentication);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/checkAuth")
    public ResponseEntity<Map<String,String>> checkAuth(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            String role = isAdmin ? "admin" : "user";
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("role", role));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
