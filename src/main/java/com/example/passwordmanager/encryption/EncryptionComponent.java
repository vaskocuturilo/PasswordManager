package com.example.passwordmanager.encryption;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class EncryptionComponent {
    @Value("${cipher.key}")
    private String key;
    private String transformationType = "AES/GCM/NoPadding";
    private String algorithm = "AES";

    public String encrypt(String value) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] bytesIV = new byte[16];
            random.nextBytes(bytesIV);
            GCMParameterSpec iv = new GCMParameterSpec(128, bytesIV);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance(transformationType);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    public String decrypt(String encrypted) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] bytesIV = new byte[16];
            random.nextBytes(bytesIV);
            GCMParameterSpec iv = new GCMParameterSpec(128, bytesIV);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance(transformationType);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }
}
