package org.eventmanagement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class EventStatisticsDtoForUser {
    @NonNull
    private Long totalEvents;

    @NonNull
    private Long createdEvents;
    @NonNull
    private Long approvedEvents;
    @NonNull
    private Long pendingEvents;
}
