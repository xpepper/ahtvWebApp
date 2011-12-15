package it.matrix.alicehometv.servlet.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.ControlCodeVerifier;
import it.matrix.alicehometv.ServiceResponse;
import it.matrix.alicehometv.servlet.EmailControlCodeCheckServlet;
import it.telecomitalia.rossoalice.aa.sandbox.constants.Parameters;

import java.net.MalformedURLException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;


public class TestEmailControlCodeCheckServlet extends ServletTestFixture
{
    private Mockery context = new JUnit4Mockery();
    private ControlCodeVerifier mockControlCodeVerifier;

    @Before
    public void setUp()
    {
        mockControlCodeVerifier = context.mock(ControlCodeVerifier.class);
    }

    @Test
    public void whenVerificationSucceedShouldRedirectToAvvisiRecapiti() throws Exception
    {
        ServletTestFixtureUtils.setCookieWithAhtvUserIn(123, fakeRequest());
        fakeRequest().setContextPath("/ahtv");
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "anySunrisePassportCode");
        fakeRequest().setParameter("controlCode", "aSubmittedPassword");
        
        context.checking(new Expectations()
        {{      
            one(mockControlCodeVerifier).forUser(123, "aSubmittedPassword", "email"); will(returnValue(ServiceResponse.okResponseWith("any ok message")));
        }});
        
        new EmailControlCodeCheckServletForTest(mockControlCodeVerifier).doGet(fakeRequest(), fakeResponse());

        assertEquals("/ahtv/MyHTV/avvisi_recapiti.html", fakeResponse().getRedirectedUrl());
    }
    
    @Test
    public void whenVerificationFailsShouldRedirectToAnErrorPage() throws Exception
    {
        ServletTestFixtureUtils.setCookieWithAhtvUserIn(123, fakeRequest());
        fakeRequest().setServerName("alicehometv.alice.it");
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "anySunrisePassportCode");
        fakeRequest().setParameter("controlCode", "aSubmittedPassword");
        
        context.checking(new Expectations()
        {{      
            one(mockControlCodeVerifier).forUser(123, "aSubmittedPassword", "email"); will(returnValue(ServiceResponse.errorResponseWith("any error")));
        }});
        
        new EmailControlCodeCheckServletForTest(mockControlCodeVerifier).doGet(fakeRequest(), fakeResponse());

        assertEquals("http://alicehometv.alice.it:80/Errore/index.html?Err=CONTROL_CODE_ERROR", fakeResponse().getRedirectedUrl());
    }
    
    @SuppressWarnings("serial")
    private class EmailControlCodeCheckServletForTest extends EmailControlCodeCheckServlet
    {
        private ControlCodeVerifier itsControlCodeVerifier;

        public EmailControlCodeCheckServletForTest(ControlCodeVerifier controlCodeVerifier)
        {
            itsControlCodeVerifier = controlCodeVerifier;
        }
        
        protected ControlCodeVerifier controlCodeVerifier() throws MalformedURLException
        {
            return itsControlCodeVerifier;
        }
    }
}
