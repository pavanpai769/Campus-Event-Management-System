package org.eventmanagement.datamanager;

import jakarta.transaction.Transactional;
import org.eventmanagement.datamanager.entity.Event;
import org.eventmanagement.datamanager.entity.User;
import org.eventmanagement.dto.EventRequestDto;
import org.eventmanagement.dto.EventStatisticsDtoForAdmin;
import org.eventmanagement.dto.EventStatisticsDtoForUser;
import org.eventmanagement.exception.ResourceNotFoundException;
import org.eventmanagement.datamanager.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventDataManager {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegistrationDataManager registrationDataManager;


    public EventStatisticsDtoForAdmin getEventStatisticsForAdmin(){

        long totalEvents = eventRepository.countTotalEvents();
        long approvedEventsSize = eventRepository.countApprovedEvents();

        return EventStatisticsDtoForAdmin.builder()
                .totalEvents(totalEvents)
                .pendingEvents(totalEvents - approvedEventsSize)
                .approvedEvents(approvedEventsSize)
                .build();
    }

    public EventStatisticsDtoForUser getEventStatisticsForUser(Long userId) {
        long totalEvents = eventRepository.countApprovedEvents();
        long createdEvents = eventRepository.countCreatedEvents(userId);
        long approvedEventsCreatedByUser = eventRepository.countApprovedEventsCreatedByUser(userId);

        return EventStatisticsDtoForUser.builder()
                .totalEvents(totalEvents)
                .createdEvents(createdEvents)
                .approvedEvents(approvedEventsCreatedByUser)
                .pendingEvents(createdEvents - approvedEventsCreatedByUser)
                .build();

    }

    public Event createEvent(EventRequestDto eventRequestDto, User host) {

        Event event = new Event();
        event.setName(eventRequestDto.getName());
        event.setDescription(eventRequestDto.getDescription());
        event.setVenue(eventRequestDto.getVenue());
        event.setDate(eventRequestDto.getDate());
        event.setTime(eventRequestDto.getTime());
        event.setHost(host);

        eventRepository.save(event);

        return event;
    }

    public Event getEventById(Long eventId) {
        return eventRepository.getEventById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + eventId));
    }

    public Event approveEvent(Long eventId) {
        Event event = getEventById(eventId);
        event.setApproved(true);
        eventRepository.save(event);
        return event;
    }

    public List<Event> getAllEvents() {
        return eventRepository.getAllEvents();
    }

    public List<Event> getAllPendingEvents() {
        return eventRepository.findByIsApprovedFalseAndIsDeletedFalse();
    }

    public List<Event> getAllApprovedEvents() {
        return eventRepository.findByIsApprovedTrueAndIsDeletedFalse();
    }

    @Transactional
    public Event deleteEvent(Long eventId) {
        Event event = getEventById(eventId);
        registrationDataManager.deleteRegistrationByEventId(eventId);
        event.setDeleted(true);
        eventRepository.save(event);
        return event;
    }

    public List<Event> getEventsCreatedByHost(Long hostId) {
        return eventRepository.findByHostId(hostId);
    }

    public List<Event> findEventsNotRegisteredByUser(Long userId){
        return eventRepository.findEventsNotRegisteredByUser(userId);
    }


    public List<Event> getAllPendingEventsByHost(Long hostId) {
        return eventRepository.getAllPendingEventsByUser(hostId);
    }

    public List<Event> getAllApprovedEventsByHost(Long hostId) {
        return eventRepository.getAllApprovedEventsByUser(hostId);
    }

    @Transactional
    public void deleteEventsByHostId(Long userId){
        List<Event> events = getEventsCreatedByHost(userId);
        events.forEach(event -> deleteEvent(event.getEventId()));
    }
}
