package com.example.passwordmanager.controller;

import com.example.passwordmanager.services.OneTimePasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/one-time-password")
public class OneTimePasswordController {

    private final OneTimePasswordService oneTimePasswordService;

    public OneTimePasswordController(OneTimePasswordService oneTimePasswordService) {
        this.oneTimePasswordService = oneTimePasswordService;
    }

    @PostMapping
    private ResponseEntity getOneTimePassword(@RequestParam UUID userId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(oneTimePasswordService.returnOneTimePassword(userId));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(exception.getMessage());
        }
    }
}
