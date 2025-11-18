package org.eventmanagement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class RegistrationResponseDto {

    @NonNull
    private Long registrationId;

    @NonNull
    private EventResponseDto event;

}
