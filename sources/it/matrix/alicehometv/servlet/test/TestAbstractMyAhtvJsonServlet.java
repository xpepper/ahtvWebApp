package it.matrix.alicehometv.servlet.test;

import static it.matrix.alicehometv.servlet.test.ServletTestFixtureUtils.*;
import static org.junit.Assert.*;
import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.servlet.AbstractMyAhtvJsonServlet;
import it.telecomitalia.rossoalice.aa.sandbox.constants.Parameters;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class TestAbstractMyAhtvJsonServlet extends ServletTestFixture
{
    @Test
    public void shouldRespondWithProperMessageWhenLoggedUserIsNotAnIptvUser() throws Exception
    {
        setCookieWithNonAhtvUserIn(fakeRequest());
        
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "anySunrisePassportCode");
        
        new AbstractMyAhtvJsonServletForTest().doGet(fakeRequest(), fakeResponse());

        assertEquals(
                "{" +
                    "\"responseMessage\":{\"itsResult\":\"ERROR\",\"itsMessage\":\"not an iptv user\"}" + 
                "}", 
                fakeResponse().getContentAsString());        
    }
    
    @Test
    public void shouldRespondWithProperMessageWhenWhenAhtvCookieIsNotFound() throws Exception
    {
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "anySunrisePassportCode");
        setNoCookiesIn(fakeRequest());
        
        new AbstractMyAhtvJsonServletForTest().doGet(fakeRequest(), fakeResponse());

        assertEquals(
                "{" +
                    "\"responseMessage\":{\"itsResult\":\"ERROR\",\"itsMessage\":\"not an iptv user\"}" + 
                "}", 
                fakeResponse().getContentAsString());        
    }
    
    @Test
    public void shouldRespondWithAnErrorMessageWhenAServiceThrowAnException() throws Exception
    {
        ActivityLogger.off();
        
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "anySunrisePassportCode");
        setCookieWithAhtvUserIn(fakeRequest());
        
        new FailingAbstractMyAhtvJsonServletForTest().doGet(fakeRequest(), fakeResponse());

        assertEquals(
                "{" +
                    "\"responseMessage\":{\"itsResult\":\"ERROR\",\"itsMessage\":\"A generic error occurred\"}" + 
                "}", 
                fakeResponse().getContentAsString());
    }

    @After
    public void resumeLog()
    {
        ActivityLogger.resume();        
    }
    
    @SuppressWarnings("serial")
    private class AbstractMyAhtvJsonServletForTest extends AbstractMyAhtvJsonServlet
    {
        protected String runOn(XStream converter, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException
        {
            return null;
        }
    }
    
    @SuppressWarnings("serial")
    private class FailingAbstractMyAhtvJsonServletForTest extends AbstractMyAhtvJsonServlet
    {
        protected String runOn(XStream converter, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException
        {
            throw new RuntimeException("generated exception for test");
        }
    }
}
