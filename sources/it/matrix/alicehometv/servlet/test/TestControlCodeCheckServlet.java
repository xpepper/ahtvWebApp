package it.matrix.alicehometv.servlet.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.ControlCodeVerifier;
import it.matrix.alicehometv.ServiceResponse;
import it.matrix.alicehometv.servlet.ControlCodeCheckServlet;
import it.telecomitalia.rossoalice.aa.sandbox.constants.Parameters;

import java.net.MalformedURLException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;


public class TestControlCodeCheckServlet extends ServletTestFixture
{
    private Mockery context = new JUnit4Mockery();

    private ControlCodeVerifier mockControlCodeVerifier;

    @Before
    public void setUp()
    {
        mockControlCodeVerifier = context.mock(ControlCodeVerifier.class);
    }

    @Test
    public void test() throws Exception
    {
        ServletTestFixtureUtils.setCookieWithAhtvUserIn(123, fakeRequest());
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "anySunrisePassportCode");
        fakeRequest().setParameter("controlCode", "aSubmittedPassword");
        fakeRequest().setParameter("controlCodeType", "email");
        
        context.checking(new Expectations()
        {{      
            one(mockControlCodeVerifier).forUser(123, "aSubmittedPassword", "email"); will(returnValue(ServiceResponse.okResponseWith("any ok message")));
        }});
        
        new ControlCodeCheckServletForTest(mockControlCodeVerifier).doGet(fakeRequest(), fakeResponse());

        assertEquals("{\"responseMessage\":{\"itsResult\":\"OK\",\"itsMessage\":\"any ok message\"}}", fakeResponse().getContentAsString());
    }
    
    @SuppressWarnings("serial")
    private class ControlCodeCheckServletForTest extends ControlCodeCheckServlet
    {
        private ControlCodeVerifier itsControlCodeVerifier;

        public ControlCodeCheckServletForTest(ControlCodeVerifier controlCodeVerifier)
        {
            itsControlCodeVerifier = controlCodeVerifier;
        }
        
        protected ControlCodeVerifier controlCodeVerifier() throws MalformedURLException
        {
            return itsControlCodeVerifier;
        }
    }
}
