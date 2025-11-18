package org.eventmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UserResponseDtoForAdmin{

    @NonNull
    @JsonProperty("user Id")
    private Long userId;

    @NonNull
    private String name;

    @NonNull
    private String username;

    @JsonProperty("status")
    private Boolean isApproved;

    @NonNull
    private String email;

}
