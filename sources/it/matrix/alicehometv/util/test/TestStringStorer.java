package it.matrix.alicehometv.util.test;

import static it.matrix.alicehometv.util.test.AssertUtils.*;
import static org.junit.Assert.*;
import it.matrix.alicehometv.util.EncryptionUtils;
import it.matrix.alicehometv.util.StringStorer;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class TestStringStorer
{

    @Test
    public void testToString() throws Exception
    {
        StringStorer storer = new StringStorer();

        storer.put("key", "value");

        assertEquals("key=value", storer.toString());
    }

    @Test
    public void shouldReturnNullWhenKeyIsNotExisting() throws Exception
    {
        assertNull(new StringStorer().get("notExistingKey"));
    }

    @Test
    public void testWithEmptyValues() throws Exception
    {
        StringStorer storer = new StringStorer();

        storer.put("key", null);
        assertNull(storer.get("key"));

        storer.put("key", StringUtils.EMPTY);
        assertEmpty(storer.get("key"));
    }

    @Test
    public void testPutAndGet() throws Exception
    {
        StringStorer storer = new StringStorer();
        storer.put("key", "value");
        assertEquals("value", storer.get("key"));
    }

    @Test
    public void testAsEncryptedString() throws Exception
    {
        String emptyStringEncrypted = EncryptionUtils.encrypt(StringUtils.EMPTY);
        assertEquals(emptyStringEncrypted, new StringStorer().asEncryptedString());

        StringStorer storer = new StringStorer();
        storer.put("key", "value");

        assertEquals(EncryptionUtils.encrypt("key=value"), storer.asEncryptedString());

        storer.put("key2", "value2");

        assertEquals(EncryptionUtils.encrypt("key2=value2|key=value"), storer.asEncryptedString());
    }

}
