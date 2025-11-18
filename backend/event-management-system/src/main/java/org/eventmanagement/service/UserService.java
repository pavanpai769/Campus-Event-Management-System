package org.eventmanagement.service;

import org.eventmanagement.datamanager.UserDataManager;
import org.eventmanagement.dto.*;
import org.eventmanagement.datamanager.entity.User;
import org.eventmanagement.utility.DtoMappingUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDataManager userDataManager;

    @Autowired
    private EventService eventService;

    @Autowired
    private RegistrationService registrationService;

    public UserResponseDtoForUser createUser(UserRequestDto userRequestDTO) {
        User user = userDataManager.createUser(userRequestDTO);
        return DtoMappingUtility.toUserResponseDTO(user);
    }

    public List<EventResponseDto> getAvailableToJoinEvents(Authentication auth) {
        String username = auth.getName();
        User user = userDataManager.getUserByUsername(username);

        return eventService.getAvailableToJoinEvents(user.getUserId());
    }

    public List<RegistrationResponseDto> getRegisteredEvents(Authentication auth) {
        String username = auth.getName();
        User user = userDataManager.getUserByUsername(username);

        return registrationService.getAllRegistrations(user.getUserId());
    }

    public List<EventResponseDto> getCreatedEvents(Authentication auth) {
        String username = auth.getName();
        User user = userDataManager.getUserByUsername(username);

        return eventService.getEventsCreatedByUser(user.getUserId());
    }

    public List<EventResponseDto> getPendingEvents(Authentication auth) {
        String username = auth.getName();
        User user = userDataManager.getUserByUsername(username);

        return eventService.getAllPendingEventsByUser(user.getUserId());
    }

    public StatisticDtoForUser getStatistics(Authentication auth) {
        String username = auth.getName();
        User user = userDataManager.getUserByUsername(username);

        EventStatisticsDtoForUser eventStatistics = eventService.getEventStatisticsForUser(user.getUserId());
        RegistrationsStatisticsDtoForUser registrationStatistics = registrationService.getRegistrationStatisticsForUser(user.getUserId());

        long totalEvents = eventStatistics.getTotalEvents();
        long registered = registrationStatistics.getRegisteredEvents();


        long createdEvents = eventStatistics.getCreatedEvents();
        long pendingEvents = eventStatistics.getPendingEvents();
        long approvedEvents = eventStatistics.getApprovedEvents();

        long availableToJoin = totalEvents - registered - approvedEvents;

        return StatisticDtoForUser.builder()
                .totalEvents(totalEvents)
                .registered(registered)
                .availableToJoin(availableToJoin)
                .createdEvents(createdEvents)
                .approvedEvents(approvedEvents)
                .pendingEvents(pendingEvents)
                .build();
    }

    public List<EventResponseDto> getApprovedEvents(Authentication auth) {
        String username = auth.getName();
        User user = userDataManager.getUserByUsername(username);

        return eventService.getAllApprovedEventsByUser(user.getUserId());
    }

    public UserResponseDtoForUser getUserByUsername(String username) {
        User user = userDataManager.getUserByUsername(username);
        return DtoMappingUtility.toUserResponseDTO(user);
    }

    public RegistrationResponseDto unregisterEvent(Long eventId, Authentication auth) {
        String username = auth.getName();
        User user = userDataManager.getUserByUsername(username);
        return registrationService.unregister(user.getUserId(),eventId);
    }

    public RegistrationResponseDto registerEvent(Long eventId, Authentication auth) {
        String username = auth.getName();
        User user = userDataManager.getUserByUsername(username);
        return registrationService.register(eventId,user);
    }

}
