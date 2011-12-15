package it.matrix.alicehometv.util.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.util.*;

import javax.servlet.http.Cookie;

import org.junit.Test;


public class TestCookieValueDetector
{
    @Test
    public void noCookieDetectedOnNullCookieArray() throws Exception
    {
        CookieValueDetector cookieValueDetector = new CookieValueDetector(null);
        
        assertNull(cookieValueDetector.detect("a_cookie_name"));
        assertNull(cookieValueDetector.valueOf("a_cookie_name"));
    }

    
    @Test
    public void noCookieDetectedOnEmptyCookieArray() throws Exception
    {
        CookieValueDetector cookieValueDetector = new CookieValueDetector(new Cookie[0]);
        
        assertNull(cookieValueDetector.detect("a_cookie_name"));
        assertNull(cookieValueDetector.valueOf("a_cookie_name"));
    }
    
    @Test
    public void noCookieDetectedWithNotMatchingName() throws Exception
    {
        Cookie[] cookies = new Cookie[] {new Cookie("one_cookie", "a value")};
        CookieValueDetector cookieValueDetector = new CookieValueDetector(cookies);
        
        assertNull(cookieValueDetector.detect("another_cookie_name"));
        assertNull(cookieValueDetector.valueOf("another_cookie_name"));
    }
    
    @Test
    public void detectCookieWithMatchingName() throws Exception
    {
        Cookie cookie = new Cookie("a_cookie_name", "a value");
        CookieValueDetector cookieValueDetector = new CookieValueDetector(new Cookie[] {cookie});

        assertEquals(cookie, cookieValueDetector.detect("a_cookie_name"));
        assertEquals("a value", cookieValueDetector.valueOf("a_cookie_name"));
    }
    
    @Test
    public void detectAhtvCookie() throws Exception
    {
        AhtvCookie ahtvCookie = new AhtvCookie();
        ahtvCookie.setDomain("any.pattern");
        
        CookieValueDetector cookieValueDetector = new CookieValueDetector(new Cookie[] {ahtvCookie});

        Cookie actualCookie = cookieValueDetector.detectAhtvCookie();
        assertEquals(ahtvCookie.getName(), actualCookie.getName());
        assertEquals(ahtvCookie.getValue(), cookieValueDetector.detectAhtvCookie().getValue());
        assertEquals("any.pattern", cookieValueDetector.detectAhtvCookie().getDomain());
    }

    @Test
    public void detectAhtvCookieWithNullObject() throws Exception
    {
        assertTrue(new CookieValueDetector(new Cookie[0]).detectAhtvCookie() instanceof NullAhtvCookie);
    }

}
