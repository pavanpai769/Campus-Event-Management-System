package org.eventmanagement.datamanager;

import jakarta.transaction.Transactional;
import org.eventmanagement.datamanager.entity.User;
import org.eventmanagement.dto.UserStatisticsDtoForAdmin;
import org.eventmanagement.dto.UserRequestDto;
import org.eventmanagement.exception.ResourceNotFoundException;
import org.eventmanagement.exception.UserAlreadyExistsException;
import org.eventmanagement.datamanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDataManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventDataManager eventDataManager;

    @Autowired
    private RegistrationDataManager registrationDataManager;


    @Autowired
    private PasswordEncoder passwordEncoder;


    public User createUser(UserRequestDto userRequestDTO) {

        if(userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if(userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        userRepository.save(user);

        return user;
    }

    public UserStatisticsDtoForAdmin getUserStatistics(){

        long pendingUsersSize = userRepository.countPendingUsers();
        long approvedUsersSize = userRepository.countApprovedUsers();

        return UserStatisticsDtoForAdmin.builder()
                .totalUsers(pendingUsersSize+approvedUsersSize)
                .pendingUsers(pendingUsersSize)
                .approvedUsers(approvedUsersSize)
                .build();
    }

    public User getUserById(Long userId) {
        return userRepository.getUserById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User with id " + userId + " not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public List<User> getAllPendingUsers() {
        return userRepository.findByIsApprovedFalseAndIsDeletedFalse();
    }

    public List<User> getAllApprovedUsers() {
        return userRepository.findByIsApprovedTrueAndIsDeletedFalse();
    }

    public User getUserByUsername(String userName) {
        return userRepository.getUserByUsername(userName)
                .orElseThrow(()-> new ResourceNotFoundException("User with username"+ userName+"Not found"));
    }

    public User approveUser(Long userId) {
        User user = getUserById(userId);

        user.setApproved(true);
        user.getRoles().add("USER");

        userRepository.save(user);

        return user;
    }

    @Transactional
    public User deleteUser(Long userId) {
        User user = getUserById(userId);
        eventDataManager.deleteEventsByHostId(userId);
        registrationDataManager.deleteRegistrationByUserId(userId);
        user.setDeleted(true);
        userRepository.save(user);
        return user;
    }
}
