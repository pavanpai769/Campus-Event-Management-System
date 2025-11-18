package org.eventmanagement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class RegistrationsStatisticsDtoForUser {
    @NonNull
    private Long registeredEvents;
}
