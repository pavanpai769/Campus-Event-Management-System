package org.eventmanagement.controller;

import org.eventmanagement.dto.EventResponseDto;
import org.eventmanagement.dto.RegistrationResponseDto;
import org.eventmanagement.dto.StatisticDtoForUser;
import org.eventmanagement.dto.UserResponseDtoForUser;
import org.eventmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/statistics")
    public ResponseEntity<StatisticDtoForUser> getStatistics() {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        StatisticDtoForUser statistics = userService.getStatistics(auth);
        return ResponseEntity.status(HttpStatus.OK).body(statistics);
    }

    @GetMapping("/available-to-join")
    public ResponseEntity<List<EventResponseDto>> getAvailableToJoinEvents() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<EventResponseDto> eventsCreatedByUser = userService.getAvailableToJoinEvents(auth);
        return ResponseEntity.status(HttpStatus.OK).body(eventsCreatedByUser);
    }

    @GetMapping("/registered")
    public ResponseEntity<List<RegistrationResponseDto>> getRegisteredEvents() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<RegistrationResponseDto> registrations = userService.getRegisteredEvents(auth);
        return ResponseEntity.status(HttpStatus.OK).body(registrations);
    }

    @GetMapping("/created-events")
    public ResponseEntity<List<EventResponseDto>> getCreatedEvents() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<EventResponseDto> createdEvents = userService.getCreatedEvents(auth);
        return ResponseEntity.status(HttpStatus.OK).body(createdEvents);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<EventResponseDto>> getPendingEvents() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<EventResponseDto> pendingEvents = userService.getPendingEvents(auth);
        return ResponseEntity.status(HttpStatus.OK).body(pendingEvents);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<EventResponseDto>> getApprovedEvents() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<EventResponseDto> approvedEvents = userService.getApprovedEvents(auth);
        return ResponseEntity.status(HttpStatus.OK).body(approvedEvents);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDtoForUser> getEvent(@PathVariable("username") String username) {
        UserResponseDtoForUser user = userService.getUserByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{eventId}/unregister")
    public ResponseEntity<RegistrationResponseDto> unregisterEvent(@PathVariable("eventId")Long eventId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RegistrationResponseDto registration = userService.unregisterEvent(eventId,auth);
        return ResponseEntity.status(HttpStatus.OK).body(registration);
    }

    @PostMapping("/{eventId}/register")
    public ResponseEntity<RegistrationResponseDto> registerEvent(@PathVariable("eventId") Long eventId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RegistrationResponseDto registration = userService.registerEvent(eventId,auth);
        return ResponseEntity.status(HttpStatus.CREATED).body(registration);
    }


}
