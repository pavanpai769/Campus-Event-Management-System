package org.eventmanagement.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class UserRequestDto {
    @NonNull
    private String name;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String email;
}
