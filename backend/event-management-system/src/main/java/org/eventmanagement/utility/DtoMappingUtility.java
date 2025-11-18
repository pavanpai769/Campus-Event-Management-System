package org.eventmanagement.utility;

import lombok.experimental.UtilityClass;
import org.eventmanagement.dto.*;
import org.eventmanagement.datamanager.entity.Event;
import org.eventmanagement.datamanager.entity.Registration;
import org.eventmanagement.datamanager.entity.User;

import java.util.List;

@UtilityClass
public class DtoMappingUtility {

    public UserResponseDtoForUser toUserResponseDTO(User user) {
        return UserResponseDtoForUser.builder()
                .name(user.getName())
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public List<UserResponseDtoForUser> toUserResponseDtoList(List<User> users) {
        return users.stream()
                .map(DtoMappingUtility::toUserResponseDTO)
                .toList();
    }


    public static RegistrationResponseDto toRegistrationResponseDto(Registration registration) {
        return RegistrationResponseDto.builder()
                .registrationId(registration.getRegistrationId())
                .event(toEventResponseDto(registration.getEvent()))
                .build();
    }

    public static List<RegistrationResponseDto> toRegistrationResponseDtoList(List<Registration> registrations) {
        return registrations.stream()
                .map(DtoMappingUtility::toRegistrationResponseDto)
                .toList();
    }
    public static EventResponseDto toEventResponseDto(Event event) {
        return EventResponseDto.builder()
                .eventId(event.getEventId())
                .name(event.getName())
                .description(event.getDescription())
                .date(event.getDate())
                .time(event.getTime())
                .venue(event.getVenue())
                .isApproved(event.isApproved())
                .hostUsername(event.getHost().getUsername())
                .build();

    }

    public static List<EventResponseDto> toEventResponseDtoList(List<Event> events) {
        return events.stream()
                .map(DtoMappingUtility::toEventResponseDto)
                .toList();
    }

    public static List<UserResponseDtoForAdmin> toUserResponseDTOForAdminForAdminList(List<User> users) {
        return users.stream()
                .map(DtoMappingUtility::toUserResponseDTOForAdmin)
                .toList();
    }

    public static UserResponseDtoForAdmin toUserResponseDTOForAdmin(User user) {
        return UserResponseDtoForAdmin.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .isApproved(user.isApproved())
                .build();
    }
}
