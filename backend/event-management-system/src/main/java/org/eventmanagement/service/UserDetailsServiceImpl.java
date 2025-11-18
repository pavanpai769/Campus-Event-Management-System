package org.eventmanagement.service;

import org.eventmanagement.datamanager.UserDataManager;
import org.eventmanagement.datamanager.entity.User;
import org.eventmanagement.exception.ResourceNotFoundException;
import org.eventmanagement.datamanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserDataManager userDataManager;

    @Override
    public UserDetails loadUserByUsername(String username){
        User userInDb = userDataManager.getUserByUsername(username);

        return org.springframework.security.core.userdetails.User.builder()
                .username(userInDb.getUsername())
                .password(userInDb.getPassword())
                .roles(userInDb.getRoles().toArray(String[]::new))
                .build();
    }
}
