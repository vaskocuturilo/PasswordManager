package com.example.passwordmanager.controller;

import com.example.passwordmanager.services.SubscribeService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subscribes")
@AllArgsConstructor
public class SubscribeController {
    private final SubscribeService subscribeService;

    @PostMapping("/{email}/on")
    public ResponseEntity createSubscribe(@PathVariable String email) {
        try {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(subscribeService.createSubscribe(email));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/{email}/off")
    public ResponseEntity createUnSubscribe(@PathVariable String email) {
        try {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(subscribeService.createUnSubscribe(email));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
