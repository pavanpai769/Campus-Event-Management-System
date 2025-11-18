package org.eventmanagement.controller;
import org.eventmanagement.dto.EventRequestDto;
import org.eventmanagement.dto.EventResponseDto;
import org.eventmanagement.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping("/create-event")
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto eventRequestDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        EventResponseDto eventResponseDtoForUser = eventService.createEvent(eventRequestDto,auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventResponseDtoForUser);
    }
}
