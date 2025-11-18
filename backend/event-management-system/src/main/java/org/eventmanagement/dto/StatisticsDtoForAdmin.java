package org.eventmanagement.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class StatisticsDtoForAdmin {
    @NonNull
    private Long totalEvents;

    @NonNull
    private Long approvedEvents;

    @NonNull
    private Long pendingEvents;

    @NonNull
    private Long totalUsers;

    @NonNull
    private Long approvedUsers;

    @NonNull
    private Long pendingUsers;
}
