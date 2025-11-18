package org.eventmanagement.controller;

import org.eventmanagement.dto.EventResponseDto;
import org.eventmanagement.dto.StatisticsDtoForAdmin;
import org.eventmanagement.dto.UserResponseDtoForAdmin;
import org.eventmanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsDtoForAdmin> getStatistics() {
        StatisticsDtoForAdmin statistics = adminService.getStatistics();
        return ResponseEntity.status(HttpStatus.OK).body(statistics);
    }

    @GetMapping("/users/pending")
    public ResponseEntity<List<UserResponseDtoForAdmin>> getAllPendingUsers() {
        List<UserResponseDtoForAdmin> pendingUsers = adminService.getAllPendingUsers();
        return ResponseEntity.status(HttpStatus.OK).body(pendingUsers);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDtoForAdmin>> getAllUsers() {
        List<UserResponseDtoForAdmin> users = adminService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/users/approved")
    public ResponseEntity<List<UserResponseDtoForAdmin>> getAllApprovedUsers() {
        List<UserResponseDtoForAdmin> pendingUsers = adminService.getAllApprovedUsers();
        return ResponseEntity.status(HttpStatus.OK).body(pendingUsers);
    }

    @PatchMapping("/users/{userId}/approve")
    public ResponseEntity<UserResponseDtoForAdmin> approveUser(@PathVariable Long userId) {
        UserResponseDtoForAdmin approvedUser = adminService.approveUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(approvedUser);
    }

    @DeleteMapping("/users/{userId}/delete")
    public ResponseEntity<UserResponseDtoForAdmin> deleteUser(@PathVariable Long userId) {
        UserResponseDtoForAdmin deletedUser = adminService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(deletedUser);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        List<EventResponseDto> allEvents = adminService.getAllEvents();
        return ResponseEntity.status(HttpStatus.OK).body(allEvents);
    }

    @GetMapping("/events/approved")
    public ResponseEntity<List<EventResponseDto>> getAllApprovedEvents() {
        List<EventResponseDto> approvedEvents = adminService.getAllApprovedEvents();
        return ResponseEntity.status(HttpStatus.OK).body(approvedEvents);
    }

    @GetMapping("/events/pending")
    public ResponseEntity<List<EventResponseDto>> getAllPendingEvents() {
        List<EventResponseDto> pendingEvents = adminService.getAllPendingEvents();
        return ResponseEntity.status(HttpStatus.OK).body(pendingEvents);
    }

    @PatchMapping("/events/{eventId}/approve")
    public ResponseEntity<EventResponseDto> approveEvent(@PathVariable Long eventId) {
        EventResponseDto approvedEvent = adminService.approveEvent(eventId);
        return ResponseEntity.status(HttpStatus.OK).body(approvedEvent);
    }

    @DeleteMapping("/events/{eventId}/delete")
    public ResponseEntity<EventResponseDto> deleteEvent(@PathVariable Long eventId) {
        EventResponseDto deletedEvent = adminService.deleteEvent(eventId);
        return ResponseEntity.status(HttpStatus.OK).body(deletedEvent);
    }

}
