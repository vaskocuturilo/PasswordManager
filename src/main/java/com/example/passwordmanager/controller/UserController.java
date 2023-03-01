package com.example.passwordmanager.controller;

import com.example.passwordmanager.domain.UserEntity;
import com.example.passwordmanager.exceptions.UserAlreadyExist;
import com.example.passwordmanager.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserEntity user) {
        try {
            return ResponseEntity.ok(userService.createUser(user));
        } catch (UserAlreadyExist exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
