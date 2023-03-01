package com.example.passwordmanager.controller;

import com.example.passwordmanager.domain.PasswordEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.passwordmanager.services.PasswordManagerService;

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
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity createPassword(@RequestBody PasswordEntity passwordEntity) {
        try {
            return ResponseEntity.ok(passwordManagerService.create(passwordEntity));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping(value = "/passwords/all")
    public ResponseEntity getAllPasswords() {
        try {
            return ResponseEntity.ok(passwordManagerService.getAllPasswords());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updatePassword(@RequestBody PasswordEntity passwordEntity) {
        try {
            return ResponseEntity.ok(passwordManagerService.updatePassword(passwordEntity));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
