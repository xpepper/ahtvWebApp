package it.matrix.alicehometv.util.test;

import static junit.framework.Assert.*;
import static org.apache.commons.lang.StringUtils.*;

import java.util.Arrays;

import org.apache.commons.collections.CollectionUtils;

@SuppressWarnings("unchecked")
public class AssertUtils
{
    public static void assertNotEquals(Object expected, Object actual)
    {
        assertFalse(expected.equals(actual));
    }

    public static void assertNotContains(String aString, String aSubstring)
    {
        assertFalse(contains(aString, aSubstring));
    }

    public static void assertEmpty(String aString)
    {
        assertTrue(isEmpty(aString));
    }
    
    public static void assertArrayEqualsIgnoringOrder(Object[] expected, Object[] actual)
    {
        assertTrue(CollectionUtils.isEqualCollection(Arrays.asList(expected), Arrays.asList(actual)));
    }


}
