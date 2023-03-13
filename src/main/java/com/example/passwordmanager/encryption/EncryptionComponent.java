package com.example.passwordmanager.encryption;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

@Component
public class EncryptionComponent {
    @Value("${cipher.key}")
    private String key;
    private static final String CIPHER_INSTANCE_NAME = "AES/CBC/PKCS5PADDING";
    private static final String SECRET_KEY_ALGORITHM = "AES";
    private String initVector = "1234567812345678";

    public String encrypt(String value) {
        try {
            IvParameterSpec parameterSpec = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), SECRET_KEY_ALGORITHM);

            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, parameterSpec);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (GeneralSecurityException exception) {
            throw new IllegalStateException(exception.getMessage());
        }
    }

    public String decrypt(String encrypted) {
        try {
            IvParameterSpec parameterSpec = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), SECRET_KEY_ALGORITHM);

            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE_NAME);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, parameterSpec);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original);
        } catch (GeneralSecurityException exception) {
            throw new IllegalStateException(exception.getMessage());
        }
    }
}
