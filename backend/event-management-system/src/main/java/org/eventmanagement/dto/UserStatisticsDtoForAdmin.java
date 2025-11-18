package org.eventmanagement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class UserStatisticsDtoForAdmin {

    @NonNull
    private Long totalUsers;

    @NonNull
    private Long approvedUsers;

    @NonNull
    private Long pendingUsers;
}
