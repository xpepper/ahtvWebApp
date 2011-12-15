package it.matrix.alicehometv.util.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.util.*;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class TestStringStorerBuilder
{
    @Test
    public void testBuildFromEncryptedKeyValueSequence() throws Exception
    {
        String encryptedKeyValueSequence = EncryptionUtils.encrypt("key=value");

        StringStorer builtStorer = StringStorerBuilder.buildFromEncrypted(encryptedKeyValueSequence);
        
        StringStorer expectedStorer = new StringStorer().put("key", "value");
        assertEquals(expectedStorer, builtStorer);
        
        String anotherEncryptedKeyValueSequence = EncryptionUtils.encrypt("key=value|key2=value2");

        builtStorer = StringStorerBuilder.buildFromEncrypted(anotherEncryptedKeyValueSequence);
        
        expectedStorer = new StringStorer().put("key", "value").put("key2", "value2");
        assertEquals(expectedStorer, builtStorer);
    }
    
    @Test
    public void shouldReturnAnEmptyStringStorerWhenBuiltWithEmptyString() throws Exception
    {
        assertEquals(new StringStorer(), StringStorerBuilder.buildFromEncrypted(StringUtils.EMPTY));
    }

    @Test
    public void testWithAKeyWithAnEmptyValue() throws Exception
    {
        StringStorer builtStorer = StringStorerBuilder.buildFrom("key=|key2=value2");
        StringStorer expectedStorer = new StringStorer().put("key", "").put("key2", "value2");
        assertEquals(expectedStorer, builtStorer);
    }
        
    @Test
    public void shouldThrowAnExceptionWhenBuiltWithNullString() throws Exception
    {
        try
        {
            StringStorerBuilder.buildFromEncrypted(null);
            fail();
        }
        catch (RuntimeException exception)
        {
        }

    }

}
