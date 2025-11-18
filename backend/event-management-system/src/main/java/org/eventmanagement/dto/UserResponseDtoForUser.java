package org.eventmanagement.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UserResponseDtoForUser {

    @NonNull
    private Long userId;

    @NonNull
    private String name;

    @NonNull
    private String username;

    @NonNull
    private String email;

}
