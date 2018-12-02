package com.seg2505.project;

import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptTools {

    public static String  encrypt (String password ) throws Exception{
        SecretKeySpec key=generateKey("seg2505bestgroup");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,key);
        byte [] encryptvalue = cipher.doFinal(password.getBytes());
        String encryptedPassword = Base64.encodeToString(encryptvalue,Base64.DEFAULT);
        return encryptedPassword;

    }

    public static String  decrypt (String encryptpassword ) throws Exception{
        SecretKeySpec key=generateKey("seg2505bestgroup");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte [] decodedtvalue = Base64.decode(encryptpassword,Base64.DEFAULT);
        byte[]  decryptvalue = cipher.doFinal(decodedtvalue);
        String decryptedPassword = new String(decryptvalue);
        return decryptedPassword;

    }

    private static SecretKeySpec generateKey(String key_) throws Exception{
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = key_.getBytes("UTF-8");
        messageDigest.update(bytes,0,bytes.length);
        byte[] key = messageDigest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }
}
