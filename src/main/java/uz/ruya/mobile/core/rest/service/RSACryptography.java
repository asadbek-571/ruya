package uz.ruya.mobile.core.rest.service;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.Cipher;
import java.security.spec.X509EncodedKeySpec;

public class RSACryptography {

    public static PublicKey decodePublicKey(String keyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(keyStr);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(spec);
        return key;
    }
    // Shifrlash metodini yaratish
    public static String encrypt(String content, PublicKey pubKey) throws Exception {
        byte[] contentBytes = content.getBytes();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] cipherContent = cipher.doFinal(contentBytes);
        return Base64.getEncoder().encodeToString(cipherContent);  // Base64 bilan kodlash
    }

    public static void main(String[] args) {
        try {
            // publicKeyni olish
            String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCpFdN1DLAFu/UyYl6B5DAUozILWhYQgbFh9UnniriDOVBZUNf9zHSpRoAnHhsJZErqJpU/0A1nL2MfPJSyRQwHnMXXpnH/lavrOywrmpkHLls3fhKIjH9OuvWSk8x24ZGy+krxaKa0KZ3gDrEVii3vSYhWqUp9eopxnRXP8fturwIDAQAB";
            PublicKey publicKey = decodePublicKey(publicKeyString);

            // Parolni shifrlash
            String password = "Asad2001";
            String encryptedPassword = encrypt(password, publicKey);

            // Shifrlangan parolni chiqarish
            System.out.println("Encrypted Password: " + encryptedPassword);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
