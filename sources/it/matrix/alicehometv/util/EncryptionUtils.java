package it.matrix.alicehometv.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class EncryptionUtils
{
    private static final byte[] KEY_DATA = 
    { 
        (byte) 0x76, (byte) 0x6F, (byte) 0xBA, (byte) 0x39, (byte) 0x31, (byte) 0x2F, (byte) 0x0D, (byte) 0x4A, (byte) 0xA3, (byte) 0x90,
        (byte) 0x55, (byte) 0xFE, (byte) 0x55, (byte) 0x65, (byte) 0x61, (byte) 0x13, (byte) 0x34, (byte) 0x82, (byte) 0x12, (byte) 0x17, 
        (byte) 0xAC, (byte) 0x77, (byte) 0x39, (byte) 0x19 
    };

    private static final SecretKeySpec THREE_DES_KEY = new SecretKeySpec(KEY_DATA, "DESede");

    public static String encrypt(String text)
    {
        byte[] plainText = text.getBytes();

        try
        {
            Cipher cipher = Cipher.getInstance(THREE_DES_KEY.getAlgorithm()); 
            cipher.init(Cipher.ENCRYPT_MODE, THREE_DES_KEY);

            byte[] encryptedText = cipher.doFinal(plainText);
            return new String(Base64.encodeBase64(encryptedText));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encryptedText)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(THREE_DES_KEY.getAlgorithm()); 
            cipher.init(Cipher.DECRYPT_MODE, THREE_DES_KEY);

            byte[] decoded = Base64.decodeBase64(encryptedText.getBytes());
            byte[] clearedTextAsBytes = cipher.doFinal(decoded);
            return new String(clearedTextAsBytes);

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
