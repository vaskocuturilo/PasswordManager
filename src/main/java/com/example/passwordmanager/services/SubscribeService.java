package com.example.passwordmanager.services;

import com.example.passwordmanager.entity.SubscribeEntity;
import com.example.passwordmanager.repositories.SubscribeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;

    public SubscribeEntity createSubscribe(final String email) {
        SubscribeEntity subscribeEntity = subscribeRepository.findByEmail(email);
        if (subscribeEntity.isActive()) {
            throw new IllegalStateException("");
        }
        subscribeEntity.setActive(!subscribeEntity.isActive());

        return subscribeRepository.save(subscribeEntity);
    }

    public SubscribeEntity createUnSubscribe(final String email) {
        SubscribeEntity subscribeEntity = subscribeRepository.findByEmail(email);
        if (subscribeEntity.isActive()) {
            throw new IllegalStateException("");
        }
        subscribeEntity.setActive(!subscribeEntity.isActive());

        return subscribeRepository.save(subscribeEntity);
    }
}
