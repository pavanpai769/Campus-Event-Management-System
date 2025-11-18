package org.eventmanagement.datamanager;

import org.eventmanagement.datamanager.entity.Event;
import org.eventmanagement.datamanager.entity.Registration;
import org.eventmanagement.datamanager.entity.User;
import org.eventmanagement.datamanager.repository.RegistrationRepository;
import org.eventmanagement.dto.RegistrationsStatisticsDtoForUser;
import org.eventmanagement.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegistrationDataManager {

    @Autowired
    private RegistrationRepository registrationRepository;

    public List<Registration> findAllRegistrationsByUserId(Long userId) {
        return registrationRepository.findByUserId(userId);
    }

    public Registration findRegistrationById(Long registerId) {
        return registrationRepository.findById(registerId)
                .orElseThrow(()-> new ResourceNotFoundException("Registration with id " + registerId + " not found"));
    }

    public RegistrationsStatisticsDtoForUser getRegistrationStatisticsForUser(Long userId) {
        long registeredEvents = registrationRepository.countRegistrationsByUser(userId);

        return RegistrationsStatisticsDtoForUser.builder()
                .registeredEvents(registeredEvents)
                .build();
    }

    public Registration deleteRegistrationByUserIdAndEventId( Long userId, Long  eventId) {
        Registration registration = registrationRepository.findByUserIdAndEventId(userId,eventId)
                .orElseThrow(()-> new ResourceNotFoundException("Registration with user id "+userId +"and Event id "+ eventId+" not found"));
        registrationRepository.delete(registration);
        return registration;
    }

    public Registration register(Event event, User user) {
        Registration registration = new Registration();
        registration.setEvent(event);
        registration.setUser(user);
        registrationRepository.save(registration);
        return registration;
    }

    public void deleteRegistrationByEventId(Long eventId) {
        registrationRepository.deleteByEventId(eventId);
    }

    public void deleteRegistrationByUserId(Long userId) {
        registrationRepository.deleteByUserId(userId);
    }
}
