package it.matrix.alicehometv.util.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.util.StringUtilies;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class TestStringUtilies
{
    @Test
    public void testFirstValueFromStringArray() throws Exception
    {
        assertNull(StringUtilies.firstValueFrom(null));
        assertNull(StringUtilies.firstValueFrom(new String[0]));
        assertEquals("firstValue", StringUtilies.firstValueFrom(new String[] {"firstValue", "secondValue"} ));
    }
    
    @Test
    public void testNullSafeString() throws Exception
    {
        assertEquals(StringUtils.EMPTY, StringUtilies.nullSafeString(null));
        assertEquals("a string", StringUtilies.nullSafeString("a string"));
    }
}
