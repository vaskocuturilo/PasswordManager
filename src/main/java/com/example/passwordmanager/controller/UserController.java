package com.example.passwordmanager.controller;

import com.example.passwordmanager.entity.UserEntity;
import com.example.passwordmanager.exceptions.OneTimePasswordErrorException;
import com.example.passwordmanager.exceptions.UserAlreadyExist;
import com.example.passwordmanager.services.OneTimePasswordService;
import com.example.passwordmanager.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final OneTimePasswordService oneTimePasswordService;

    public UserController(UserService userService, OneTimePasswordService oneTimePasswordService) {
        this.userService = userService;
        this.oneTimePasswordService = oneTimePasswordService;
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
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserEntity user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok(oneTimePasswordService.returnOneTimePassword(user.getId()));
        } catch (UserAlreadyExist exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity activeUser(@RequestParam UUID id, @RequestParam Integer code) {
        try {
            return ResponseEntity.ok(userService.activeUser(id, code));
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
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
