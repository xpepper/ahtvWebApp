package it.matrix.alicehometv.servlet.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.captcha.CaptchaValidator;
import it.matrix.alicehometv.service.AhtvUserServiceFacade;
import it.matrix.alicehometv.servlet.UpdateAhtvUserServlet;
import it.telecomitalia.rossoalice.aa.sandbox.constants.Parameters;

import java.net.MalformedURLException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;

@RunWith(JMock.class)
public class TestUpdateAhtvUserServlet extends ServletTestFixture 
{
    private Mockery context = new JUnit4Mockery();

    private AhtvUserServiceFacade mockAhtvUserServiceFacade;
    private CaptchaValidator mockCaptchaValidator;

    private MockHttpSession fakeHttpSession;

    private Integer ahtvUserId;

    @Before
    public void setUp()
    {
        mockAhtvUserServiceFacade = context.mock(AhtvUserServiceFacade.class);
        mockCaptchaValidator = context.mock(CaptchaValidator.class);

        fakeHttpSession = new MockHttpSession();
        fakeRequest().setSession(fakeHttpSession);
        
        ahtvUserId = new Integer(123);
        ServletTestFixtureUtils.setCookieWithAhtvUserIn(ahtvUserId.intValue(), fakeRequest());
    }

    @Test
    public void shouldUpdateTheUser() throws Exception
    {
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "SPC1234");
        
        context.checking(new Expectations()
        {{      
            ignoring(mockCaptchaValidator);
            one(mockAhtvUserServiceFacade).updateUserWith(ahtvUserId, null, null); will(returnValue(true));
        }});
        
        new UpdateAhtvUserServletForTest(mockCaptchaValidator, mockAhtvUserServiceFacade).doPost(fakeRequest(), fakeResponse());
        
        assertEquals(
                "{" +
                    "\"responseMessage\":{\"itsResult\":\"OK\",\"itsMessage\":\"user saved successfully\"}" + 
                "}", 
                fakeResponse().getContentAsString());   
    }
    
    @Test
    public void shouldUpdateTheUserWithTheEmailProvided() throws Exception
    {
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "SPC1234");
        fakeRequest().setParameter("emailAddress", "mario.rossi@gmail.com");
        
        context.checking(new Expectations()
        {{      
            ignoring(mockCaptchaValidator);
            one(mockAhtvUserServiceFacade).updateUserWith(ahtvUserId, "mario.rossi@gmail.com", null); will(returnValue(true));
        }});
        
        new UpdateAhtvUserServletForTest(mockCaptchaValidator, mockAhtvUserServiceFacade).doPost(fakeRequest(), fakeResponse());
        
        assertEquals(
                "{" +
                    "\"responseMessage\":{\"itsResult\":\"OK\",\"itsMessage\":\"user saved successfully\"}" + 
                "}", 
                fakeResponse().getContentAsString());   
    }
    
    
    @Test
    public void shouldReturnAnErrorMessageWhenUserUpdatingFails() throws Exception
    {
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "SPC1234");

        context.checking(new Expectations()
        {{      
            ignoring(mockCaptchaValidator);
            one(mockAhtvUserServiceFacade).updateUserWith(ahtvUserId, null, null); will(returnValue(false));
        }});
        
        new UpdateAhtvUserServletForTest(mockCaptchaValidator, mockAhtvUserServiceFacade).doPost(fakeRequest(), fakeResponse());
        
        assertEquals(
                "{" +
                    "\"responseMessage\":{\"itsResult\":\"ERROR\",\"itsMessage\":\"user not saved\"}" + 
                "}", 
                fakeResponse().getContentAsString());   
    }
    
    
    @SuppressWarnings("serial")
    private class UpdateAhtvUserServletForTest extends UpdateAhtvUserServlet
    {
        private CaptchaValidator itsCaptchaValidator;
        private AhtvUserServiceFacade itsUserServiceFacade;

        public UpdateAhtvUserServletForTest(CaptchaValidator aCaptchaValidator, AhtvUserServiceFacade ahtvUserServiceFacade)
        {
            itsCaptchaValidator = aCaptchaValidator;
            itsUserServiceFacade = ahtvUserServiceFacade;
        }
        
        @Override
        protected AhtvUserServiceFacade ahtvUserServiceFacade() throws MalformedURLException
        {
            return itsUserServiceFacade;
        }
        
        protected CaptchaValidator captchaValidator()
        {
            return itsCaptchaValidator;
        }
     }
}
