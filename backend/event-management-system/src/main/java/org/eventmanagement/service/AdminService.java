package org.eventmanagement.service;

import jakarta.transaction.Transactional;
import org.eventmanagement.datamanager.EventDataManager;
import org.eventmanagement.datamanager.RegistrationDataManager;
import org.eventmanagement.datamanager.UserDataManager;
import org.eventmanagement.datamanager.entity.Event;
import org.eventmanagement.datamanager.entity.User;
import org.eventmanagement.dto.*;
import org.eventmanagement.utility.DtoMappingUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserDataManager userDataManager;

    @Autowired
    private EventDataManager eventDataManager;
    @Autowired
    private RegistrationDataManager registrationDataManager;

    public StatisticsDtoForAdmin getStatistics(){

        UserStatisticsDtoForAdmin userStatistics = userDataManager.getUserStatistics();
        EventStatisticsDtoForAdmin eventStatistics = eventDataManager.getEventStatisticsForAdmin();

        return StatisticsDtoForAdmin.builder()
                .totalEvents(eventStatistics.getTotalEvents())
                .pendingEvents(eventStatistics.getPendingEvents())
                .approvedEvents(eventStatistics.getApprovedEvents())
                //remove admin from user statistics
                .totalUsers(userStatistics.getTotalUsers()-1)
                .pendingUsers(userStatistics.getPendingUsers()-1)
                .approvedUsers(userStatistics.getApprovedUsers())
                .build();
    }

    public List<UserResponseDtoForAdmin> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<User> users = userDataManager.getAllUsers()
                .stream().filter(user-> ! user.getUsername().equals(username))
                .toList();
        return DtoMappingUtility.toUserResponseDTOForAdminForAdminList(users);
    }

    public List<UserResponseDtoForAdmin> getAllPendingUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<User> pendingUsers = userDataManager.getAllPendingUsers()
                .stream().filter(user-> ! user.getUsername().equals(username))
                .toList();
        return DtoMappingUtility.toUserResponseDTOForAdminForAdminList(pendingUsers);
    }

    public List<UserResponseDtoForAdmin> getAllApprovedUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<User> approvedUsers = userDataManager.getAllApprovedUsers()
                .stream().filter(user-> ! user.getUsername().equals(username))
                .toList();
        return DtoMappingUtility.toUserResponseDTOForAdminForAdminList(approvedUsers);
    }

    public UserResponseDtoForAdmin approveUser(Long userId) {
        userDataManager.approveUser(userId);
        return DtoMappingUtility.toUserResponseDTOForAdmin(userDataManager.approveUser(userId));
    }

    @Transactional
    public UserResponseDtoForAdmin deleteUser(Long userId) {
        User user = userDataManager.deleteUser(userId);
        return DtoMappingUtility.toUserResponseDTOForAdmin(user);
    }

    public List<EventResponseDto> getAllEvents() {
        List<Event> events = eventDataManager.getAllEvents();
        return DtoMappingUtility.toEventResponseDtoList(events);
    }

    public List<EventResponseDto> getAllPendingEvents() {
        List<Event> pendingEvents = eventDataManager.getAllPendingEvents();
        return DtoMappingUtility.toEventResponseDtoList(pendingEvents);
    }

    public List<EventResponseDto> getAllApprovedEvents() {
       List<Event> approvedEvents = eventDataManager.getAllApprovedEvents();
       return DtoMappingUtility.toEventResponseDtoList(approvedEvents);
    }

    public EventResponseDto approveEvent(Long eventId) {
        Event approvedEvent = eventDataManager.approveEvent(eventId);
        return DtoMappingUtility.toEventResponseDto(approvedEvent);
    }

    public EventResponseDto deleteEvent(Long eventId) {
        Event deletedEvent = eventDataManager.deleteEvent(eventId);
        return DtoMappingUtility.toEventResponseDto(deletedEvent);
    }

}
