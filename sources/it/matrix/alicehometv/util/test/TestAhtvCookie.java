package it.matrix.alicehometv.util.test;

import static it.matrix.alicehometv.util.test.AssertUtils.*;
import static org.junit.Assert.*;
import it.matrix.alicehometv.util.AhtvCookie;
import it.matrix.alicehometv.util.NullAhtvCookie;

import org.junit.Test;

public class TestAhtvCookie
{
    @Test
    public void testPutAndGet() throws Exception
    {
        AhtvCookie ahtvCookie = new AhtvCookie();
        assertNull(ahtvCookie.get("notExistingKey"));
        
        ahtvCookie.put("key1", null);
        assertNull(ahtvCookie.get("key1"));
        
        ahtvCookie.put("key1", "aValue");
        assertEquals("aValue", ahtvCookie.get("key1"));
    }
    
    @Test
    public void testPutTwiceWillReplacePreviousValue() throws Exception
    {
        AhtvCookie ahtvCookie = new AhtvCookie();
        
        ahtvCookie.put("key1", "aValue");
        ahtvCookie.put("key1", "aNewValue");
        
        assertEquals("aNewValue", ahtvCookie.get("key1"));
    }
    
    @Test
    public void testPutManyValues() throws Exception
    {
        AhtvCookie ahtvCookie = new AhtvCookie();
        
        ahtvCookie.put("key1", "value1");
        ahtvCookie.put("key2", "value2");
        ahtvCookie.put("key3", "value3");
        
        assertEquals("value1", ahtvCookie.get("key1"));
        assertEquals("value2", ahtvCookie.get("key2"));
        assertEquals("value3", ahtvCookie.get("key3"));
    }
    
    @Test
    public void testCookieValueIsEncrypted() throws Exception
    {
        AhtvCookie ahtvCookie = new AhtvCookie();
        
        ahtvCookie.put("key1", "value1");
        
        assertNotContains(ahtvCookie.getValue(), "key1");
        assertNotContains(ahtvCookie.getValue(), "value1");
    }
    
    
    @Test
    public void testCookieClearValue() throws Exception
    {
        AhtvCookie ahtvCookie = new AhtvCookie();
        
        ahtvCookie.put("key1", "value1");
        ahtvCookie.put("key2", "value2");
        
        assertEquals("key2=value2|key1=value1", ahtvCookie.clearedValue());
    }
    
    @Test
    public void testIsIptvUser() throws Exception
    {
        AhtvCookie ahtvCookie = new AhtvCookie();
        
        ahtvCookie.put("isIptvUser", Boolean.toString(true));
        assertTrue(ahtvCookie.isIptvUser());
        
        ahtvCookie.put("isIptvUser", Boolean.toString(false));
        assertFalse(ahtvCookie.isIptvUser());
    }
    
    @Test
    public void testAddAndGetAhtvUserId() throws Exception
    {
        AhtvCookie ahtvCookie = new AhtvCookie();
        assertNull(ahtvCookie.ahtvUserId());
        
        ahtvCookie.addAhtvUserId(123);
        
        assertEquals(new Integer(123), ahtvCookie.ahtvUserId());
    }
    
    @Test
    public void testSetAndGetSunrisePassportCode() throws Exception
    {
        AhtvCookie ahtvCookie = new AhtvCookie();
        assertNull(ahtvCookie.sunrisePassportCode());
        
        ahtvCookie.sunrisePassportCode("SPC6758");
        assertEquals("SPC6758", ahtvCookie.sunrisePassportCode());
    }
    
    @Test
    public void testHasAhtvUserId() throws Exception
    {
        AhtvCookie ahtvCookie = new AhtvCookie();
        assertTrue(ahtvCookie.hasNoAhtvUserId());
        
        ahtvCookie.addAhtvUserId(123);
        assertFalse(ahtvCookie.hasNoAhtvUserId());
    }
    
    @Test
    public void testNullAhtvCookie() throws Exception
    {
        AhtvCookie nullAhtvCookie = new NullAhtvCookie();
        assertTrue("Null Cookie is always not valid", nullAhtvCookie.isExpiredFor("anyId"));
        assertFalse(nullAhtvCookie.isIptvUser());
        assertEquals("not detected", nullAhtvCookie.clearedValue());
        
        try
        {
            nullAhtvCookie.put("aKey", "aValue");
            fail("should always raise an exception");
        }
        catch (Exception expectedException)
        {
        }
    }
    
    @Test
    public void testAhtvCookieIsExpired() throws Exception
    {
        AhtvCookie ahtvCookie = new AhtvCookie();
        assertTrue(ahtvCookie.isExpiredFor("theCurrentSunriseSessionId"));
        
        ahtvCookie.put(AhtvCookie.SUNRISE_SESSION_ID_KEY, "anOldSunriseSessionId");
        assertTrue(ahtvCookie.isExpiredFor("theCurrentSunriseSessionId"));
        
        ahtvCookie.put(AhtvCookie.SUNRISE_SESSION_ID_KEY, "theCurrentSunriseSessionId");
        assertFalse(ahtvCookie.isExpiredFor("theCurrentSunriseSessionId"));
    }

}
