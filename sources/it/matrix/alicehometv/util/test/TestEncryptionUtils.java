package it.matrix.alicehometv.util.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.util.EncryptionUtils;

import org.junit.Test;

public class TestEncryptionUtils
{
    @Test
    public void testEncryptAndDecrypt() throws Exception
    {
        String encrypted = EncryptionUtils.encrypt("a text to encrypt");
        
        assertEquals("a text to encrypt", EncryptionUtils.decrypt(encrypted));
    }

    @Test
    public void testWithEmptyString() throws Exception
    {
        assertEquals("M1VL+Sxu2Cw=", EncryptionUtils.encrypt(""));
    }

    @Test
    public void testWithNullString() throws Exception
    {
        try
        {
            EncryptionUtils.encrypt(null);
            fail("should raise a null pointer exception");
        }
        catch (NullPointerException ex)
        {
        }
        
        try
        {
            EncryptionUtils.decrypt(null);
            fail("should raise a null pointer exception");
        }
        catch (RuntimeException ex)
        {
        }
    }
}
