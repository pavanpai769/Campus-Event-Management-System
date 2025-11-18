package org.eventmanagement.controller;

import org.eventmanagement.dto.UserRequestDto;
import org.eventmanagement.dto.UserResponseDtoForUser;
import org.eventmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<UserResponseDtoForUser> createUser(@RequestBody UserRequestDto user) {
        UserResponseDtoForUser userResponseDTOForUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTOForUser);
    }
}
