package org.eventmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Builder
@Getter
public class EventResponseDto {
    @NonNull
    @JsonProperty("event Id")
    private Long eventId;

    @NonNull
    private String name;

    private String description;

    @NonNull
    private String venue;

    @NonNull
    private LocalDate date;

    @NonNull
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm a")
    private LocalTime time;

    @JsonProperty("status")
    private Boolean isApproved;

    @NonNull
    @JsonProperty("host")
    private String hostUsername;
}
