package org.eventmanagement.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class RegistrationRequestDto {
    @NonNull
    private Long eventId;
}
