package it.matrix.alicehometv.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.ServiceResponse;

import org.junit.Test;

public class TestServiceResponse
{
    @Test
    public void test() throws Exception
    {
        ServiceResponse okResponse = ServiceResponse.okResponseWith("an ok message");
        assertTrue(okResponse.isOk());
        assertEquals("an ok message", okResponse.message());
        
        ServiceResponse koResponse = ServiceResponse.errorResponseWith("an error message");
        assertFalse(koResponse.isOk());
        assertEquals("an error message", koResponse.message());
    }
}
