package com.example.passwordmanager.encryption;

import jakarta.persistence.AttributeConverter;

public class AttributeEncryptor implements AttributeConverter<String, String> {

    private final EncryptionComponent encryptionComponent;

    public AttributeEncryptor(EncryptionComponent encryptionComponent) {
        this.encryptionComponent = encryptionComponent;
    }

    @Override
    public String convertToDatabaseColumn(String s) {
        return encryptionComponent.encrypt(s);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return encryptionComponent.decrypt(s);
    }
}
