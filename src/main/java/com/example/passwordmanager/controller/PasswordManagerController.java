package com.example.passwordmanager.controller;

import com.example.passwordmanager.entity.PasswordEntity;
import com.example.passwordmanager.exceptions.PasswordNotFoundException;
import com.example.passwordmanager.exceptions.UserNotActive;
import com.example.passwordmanager.services.PasswordManagerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/passwords")
public class PasswordManagerController {
    private final PasswordManagerService passwordManagerService;

    public PasswordManagerController(PasswordManagerService passwordManagerService) {
        this.passwordManagerService = passwordManagerService;
    }

    @GetMapping("/{name}")
    public ResponseEntity getPassword(@PathVariable String name) {
        try {
            return ResponseEntity.ok(passwordManagerService.getPasswordByName(name));
        } catch (PasswordNotFoundException exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity createPassword(@RequestBody PasswordEntity passwordEntity,
                                         @RequestParam UUID userId) {
        try {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(passwordManagerService.create(passwordEntity, userId));
        } catch (UserNotActive exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(exception.getMessage());
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity getAllPasswords() {
        try {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(passwordManagerService.getAllPasswords());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updatePassword(@RequestBody PasswordEntity passwordEntity) {
        try {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(passwordManagerService.updatePassword(passwordEntity));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePassword(@PathVariable UUID id) {
        try {
            passwordManagerService.deletePassword(id);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("The password entity was delete.");
        } catch (PasswordNotFoundException exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(exception.getMessage());
        }
    }
}
