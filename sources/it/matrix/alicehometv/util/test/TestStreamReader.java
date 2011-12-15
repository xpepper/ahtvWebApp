package it.matrix.alicehometv.util.test;

import static it.matrix.alicehometv.util.test.AssertUtils.*;
import static org.junit.Assert.*;
import it.matrix.alicehometv.util.StreamReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.junit.Test;

public class TestStreamReader
{
    @Test
    public void readFromEmptyStream() throws Exception
    {
        InputStream stream = new ByteArrayInputStream(new byte[0]);
        String readChars = new StreamReader().readFrom(stream, Charset.defaultCharset().name());

        assertEmpty(readChars);
    }

    @Test
    public void readFromStream() throws Exception
    {
        InputStream stream = new ByteArrayInputStream("ciao a tutti!".getBytes());
        String readChars = new StreamReader().readFrom(stream, Charset.defaultCharset().name());

        assertFalse(readChars.isEmpty());
        assertEquals("ciao a tutti!", readChars);
    }

    @Test
    public void readFromStreamWithNotStandardASCIIChars() throws Exception
    {
        InputStream stream = new ByteArrayInputStream("Die freie Enzyklopädie\n".getBytes());
        String readChars = new StreamReader().readFrom(stream, Charset.defaultCharset().name());

        assertEquals("Die freie Enzyklopädie\n", readChars);
    }
}
