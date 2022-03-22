package com.admiralxy.springaccounts.util;

import com.google.common.hash.Hashing;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;

@Component
public class EncryptionUtils {
    private Key getKey(String password) {
        String sha256hex = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString().substring(0, 16);
        return new SecretKeySpec(sha256hex.getBytes(), "AES");
    }

    private IvParameterSpec getInitializationVector() throws Exception {
        byte[] bytes = new byte[16];
        SecureRandom random = SecureRandom.getInstanceStrong();
        random.nextBytes(bytes);
        return new IvParameterSpec(bytes);
    }

    private Cipher getCipher(int encryptMode, Key key, IvParameterSpec ivSpec) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(encryptMode, key, ivSpec);
        return cipher;
    }

    public String decode(String content, String password) throws Exception {
        byte[] encryptedRaw = Hex.decode(content);
        byte[] vector = new byte[16];
        byte[] encrypted = new byte[encryptedRaw.length - 16];
        System.arraycopy(encryptedRaw, 0, vector, 0, 16);
        int j = 0;
        for (int i = 16; i < encryptedRaw.length; i++)
            encrypted[j++] = encryptedRaw[i];
        Key key = getKey(password);
        IvParameterSpec ivSpec = new IvParameterSpec(vector);
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted);
    }

    public String encode(String content, String password) throws Exception {
        Key key = getKey(password);
        IvParameterSpec ivSpec = getInitializationVector();
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encrypted = cipher.doFinal(content.getBytes());
        byte[] result = new byte[ivSpec.getIV().length + encrypted.length];
        int i = 0;
        for (byte b : ivSpec.getIV()) {
            result[i++] = b;
        }
        for (byte b : encrypted) {
            result[i++] = b;
        }
        return new String(Hex.encode(result));
    }
}
