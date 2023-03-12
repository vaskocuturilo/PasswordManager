package com.example.passwordmanager.controller;

import com.example.passwordmanager.entity.UserEntity;
import com.example.passwordmanager.exceptions.*;
import com.example.passwordmanager.services.UserService;
import jakarta.validation.Valid;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity createUser(@Valid @RequestBody UserEntity user, UriComponentsBuilder uriComponentsBuilder) {
        try {
            return ResponseEntity.created(uriComponentsBuilder
                            .path("/api/v1/users/")
                            .build()
                            .toUri())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(userService.createUser(user));
        } catch (UserAlreadyExist exception) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exception.getMessage());
        } catch (UserHasNotContent exception) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity activeUser(@RequestParam UUID userId, @RequestParam Integer code) {
        try {
            userService.activeUser(userId, code);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("The user with id = " + userId + " was active.");
        } catch (UserAlreadyActive exception) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exception.getMessage());
        } catch (OneTimePasswordErrorException exception) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("The user with id = " + id + " was delete");
        } catch (UserNotFound exception) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exception.getMessage());
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity
                    .notFound()
                    .build();
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(exception.getMessage());
        }
    }
}
