package org.eventmanagement.service;

import org.eventmanagement.datamanager.EventDataManager;
import org.eventmanagement.datamanager.UserDataManager;
import org.eventmanagement.datamanager.entity.User;
import org.eventmanagement.dto.EventRequestDto;
import org.eventmanagement.dto.EventResponseDto;
import org.eventmanagement.datamanager.entity.Event;
import org.eventmanagement.dto.EventStatisticsDtoForUser;
import org.eventmanagement.utility.DtoMappingUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventDataManager eventDataManager;

    @Autowired
    private UserDataManager userDataManager;

    public EventResponseDto createEvent(EventRequestDto eventRequestDto, Authentication auth) {

        String username = auth.getName();

        User host = userDataManager.getUserByUsername(username);


        Event event = eventDataManager.createEvent(eventRequestDto, host);

        return DtoMappingUtility.toEventResponseDto(event);
    }

    public EventResponseDto approveEvent(Long eventId) {
        Event event = eventDataManager.approveEvent(eventId);
        return DtoMappingUtility.toEventResponseDto(event);
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

    public EventResponseDto deleteEvent(Long eventId) {
        Event event = eventDataManager.deleteEvent(eventId);

        return DtoMappingUtility.toEventResponseDto(event);
    }

    public List<EventResponseDto> getEventsCreatedByUser(Long userId) {
        User user = userDataManager.getUserById(userId);
        List<Event> events = eventDataManager.getEventsCreatedByHost(user.getUserId());
        return DtoMappingUtility.toEventResponseDtoList(events);
    }

    public List<EventResponseDto> getAvailableToJoinEvents(Long userId) {
        List<Event> availableEvents = eventDataManager.findEventsNotRegisteredByUser(userId)
                .stream().filter(event -> ! event.getHost().getUserId().equals(userId))
                .toList();
        return DtoMappingUtility.toEventResponseDtoList(availableEvents);
    }

    public List<EventResponseDto> getAllPendingEventsByUser(Long userId) {
        List<Event> pendingEventsByUser = eventDataManager.getAllPendingEventsByHost(userId);
        return DtoMappingUtility.toEventResponseDtoList(pendingEventsByUser);
    }

    public EventStatisticsDtoForUser getEventStatisticsForUser(Long userId) {
        return eventDataManager.getEventStatisticsForUser(userId);
    }

    public List<EventResponseDto> getAllApprovedEventsByUser(Long userId) {
        List<Event> approvedEventsByUser = eventDataManager.getAllApprovedEventsByHost(userId);
        return DtoMappingUtility.toEventResponseDtoList(approvedEventsByUser);
    }
}
