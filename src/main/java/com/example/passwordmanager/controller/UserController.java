package com.example.passwordmanager.controller;

import com.example.passwordmanager.entity.UserEntity;
import com.example.passwordmanager.exceptions.OneTimePasswordErrorException;
import com.example.passwordmanager.exceptions.UserAlreadyActive;
import com.example.passwordmanager.exceptions.UserAlreadyExist;
import com.example.passwordmanager.exceptions.UserNotFound;
import com.example.passwordmanager.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneUser(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(userService.getOneUser(id));
        } catch (UserNotFound exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
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

    @PutMapping
    public ResponseEntity activeUser(@RequestParam UUID userId, @RequestParam Integer code) {
        try {
            userService.activeUser(userId, code);
            return ResponseEntity.ok("The user with id = " + userId + " was active.");
        } catch (UserAlreadyActive exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (OneTimePasswordErrorException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User was delete.");
        } catch (UserNotFound exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
