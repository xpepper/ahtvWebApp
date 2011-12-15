package it.matrix.alicehometv.servlet.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.servlet.ImageCaptchaServlet;

import org.junit.Test;

public class TestImageCaptchaServlet extends ServletTestFixture
{
    @Test
    public void captchaServletShouldSetTheResponseHeaderCorrectly() throws Exception
    {
        new ImageCaptchaServlet().doGet(fakeRequest(), fakeResponse());
        
        assertEquals("image/jpeg", fakeResponse().getContentType());
        assertEquals("no-store", fakeResponse().getHeader("Cache-Control"));
        assertEquals("no-cache", fakeResponse().getHeader("Pragma"));
        assertEquals(0, fakeResponse().getHeader("Expires"));
    }
}

