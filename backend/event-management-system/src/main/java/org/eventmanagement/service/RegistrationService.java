package org.eventmanagement.service;

import org.eventmanagement.datamanager.EventDataManager;
import org.eventmanagement.datamanager.RegistrationDataManager;
import org.eventmanagement.dto.RegistrationRequestDto;
import org.eventmanagement.dto.RegistrationResponseDto;
import org.eventmanagement.datamanager.entity.Event;
import org.eventmanagement.datamanager.entity.Registration;
import org.eventmanagement.datamanager.entity.User;
import org.eventmanagement.dto.RegistrationsStatisticsDtoForUser;
import org.eventmanagement.exception.ResourceNotFoundException;
import org.eventmanagement.datamanager.repository.EventRepository;
import org.eventmanagement.datamanager.repository.RegistrationRepository;
import org.eventmanagement.datamanager.repository.UserRepository;
import org.eventmanagement.utility.DtoMappingUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationDataManager registrationDataManager;

    @Autowired
    private EventDataManager eventDataManager;

    public List<RegistrationResponseDto> getAllRegistrations(Long userId) {
       List<Registration> registrations  = registrationDataManager.findAllRegistrationsByUserId(userId);
       return DtoMappingUtility.toRegistrationResponseDtoList(registrations);
    }

    public RegistrationsStatisticsDtoForUser getRegistrationStatisticsForUser(Long userId) {
        return registrationDataManager.getRegistrationStatisticsForUser(userId);
    }


    public RegistrationResponseDto register(Long eventId, User user) {
        Event event = eventDataManager.getEventById(eventId);

        Registration registration = registrationDataManager.register(event,user);
        return DtoMappingUtility.toRegistrationResponseDto(registration);
    }

    public RegistrationResponseDto unregister(Long userId,Long eventId) {
        Registration registration = registrationDataManager.deleteRegistrationByUserIdAndEventId(userId,eventId);
        return DtoMappingUtility.toRegistrationResponseDto(registration);
    }
}
