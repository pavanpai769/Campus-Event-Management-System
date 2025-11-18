package org.eventmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
public class EventRequestDto {

    @NonNull
    private String name;

    private String description;

    @NonNull
    private String venue;

    @NonNull
    private LocalDate date;

    @NonNull
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm")
    private LocalTime time;

}
