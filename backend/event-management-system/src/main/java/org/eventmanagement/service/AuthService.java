package org.eventmanagement.service;

import org.eventmanagement.datamanager.UserDataManager;
import org.eventmanagement.datamanager.entity.User;
import org.eventmanagement.dto.UserResponseDtoForUser;
import org.eventmanagement.utility.DtoMappingUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserDataManager  userDataManager;

    public UserResponseDtoForUser getCurrentUser(Authentication authentication) {
        String username = authentication.getName();

        User user = userDataManager.getUserByUsername(username);

        return DtoMappingUtility.toUserResponseDTO(user);
    }
}
