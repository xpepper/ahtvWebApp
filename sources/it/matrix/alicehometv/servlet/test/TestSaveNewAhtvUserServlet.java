package it.matrix.alicehometv.servlet.test;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import it.matrix.alicehometv.captcha.CaptchaValidator;
import it.matrix.alicehometv.service.*;
import it.matrix.alicehometv.servlet.SaveNewAhtvUserServlet;
import it.matrix.alicehometv.util.AhtvCookie;
import it.telecomitalia.rossoalice.aa.sandbox.constants.Parameters;

import javax.servlet.http.Cookie;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;

@RunWith(JMock.class)
public class TestSaveNewAhtvUserServlet extends ServletTestFixture 
{
    private Mockery context = new JUnit4Mockery();

    private AhtvUserServiceFacade mockAhtvUserServiceFacade;
    private CaptchaValidator mockCaptchaValidator;

    private MockHttpSession fakeHttpSession;

    @Before
    public void setUp()
    {
        mockAhtvUserServiceFacade = context.mock(AhtvUserServiceFacade.class);
        mockCaptchaValidator = context.mock(CaptchaValidator.class);

        fakeHttpSession = new MockHttpSession();
        fakeRequest().setSession(fakeHttpSession);
        
        ServletTestFixtureUtils.setCookieWithAhtvUserIn(fakeRequest());
    }
  
    @Test
    public void shouldSaveTheNewUser() throws Exception
    {
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "SPC1234");
        
        context.checking(new Expectations()
        {{      
            ignoring(mockCaptchaValidator);
            one(mockAhtvUserServiceFacade).saveNewUserWith("SPC1234", null, null); will(returnValue(123));
            one(mockAhtvUserServiceFacade).isAValidId(123); will(returnValue(true));
        }});
        
        new SaveNewAhtvUserServletForTest(mockCaptchaValidator, mockAhtvUserServiceFacade).doPost(fakeRequest(), fakeResponse());
        
        assertEquals(
                "{" +
                    "\"responseMessage\":{\"itsResult\":\"OK\",\"itsMessage\":\"user saved successfully\"}" + 
                "}", 
                fakeResponse().getContentAsString());   
    }
    
    @Test
    public void shouldAddAhtvIdToAhtvCookieWhenSavingTheNewUser() throws Exception
    {
        ServletTestFixtureUtils.setCookieWithAhtvUserIn(fakeRequest());
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "SPC1234");
        
        context.checking(new Expectations()
        {{      
            ignoring(mockCaptchaValidator);
            one(mockAhtvUserServiceFacade).saveNewUserWith("SPC1234", null, null); will(returnValue(123));
            one(mockAhtvUserServiceFacade).isAValidId(123); will(returnValue(true));
        }});
        
        new SaveNewAhtvUserServletForTest(mockCaptchaValidator, mockAhtvUserServiceFacade).doPost(fakeRequest(), fakeResponse());
        
        Cookie cookie = fakeResponse().getCookie(AhtvCookie.NAME);
        assertEquals("Should contain the ahtv id", "123", new AhtvCookie(cookie).get("ahtvId"));
    }
    
    @Test
    public void shouldSaveTheNewUserWithMobileNumberAndEmailProvided() throws Exception
    {
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "SPC1234");
        fakeRequest().setParameter("mobileNumber", "+393484738746");
        fakeRequest().setParameter("emailAddress", "mario.rossi@gmail.com");
        fakeRequest().setParameter("captchaResponse", "m8TyiZ");
        
        context.checking(new Expectations()
        {{      
            one(mockCaptchaValidator).validate(fakeHttpSession.getId(), "m8TyiZ"); will(returnValue(true));
            one(mockAhtvUserServiceFacade).saveNewUserWith("SPC1234", "mario.rossi@gmail.com", "+393484738746"); will(returnValue(123));
            one(mockAhtvUserServiceFacade).isAValidId(123); will(returnValue(true));
        }});
        
        new SaveNewAhtvUserServletForTest(mockCaptchaValidator, mockAhtvUserServiceFacade).doPost(fakeRequest(), fakeResponse());
        
        assertEquals(
                "{" +
                    "\"responseMessage\":{\"itsResult\":\"OK\",\"itsMessage\":\"user saved successfully\"}" + 
                "}", 
                fakeResponse().getContentAsString());   
    }
    
    
    @Test
    public void shouldReturnAnErrorMessageWhenUserSavingFails() throws Exception
    {
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "SPC1234");

        context.checking(new Expectations()
        {{      
            ignoring(mockCaptchaValidator);
            one(mockAhtvUserServiceFacade).saveNewUserWith("SPC1234", null, null); will(returnValue(0));
            one(mockAhtvUserServiceFacade).isAValidId(0); will(returnValue(false));
        }});
        
        new SaveNewAhtvUserServletForTest(mockCaptchaValidator, mockAhtvUserServiceFacade).doPost(fakeRequest(), fakeResponse());
        
        assertEquals(
                "{" +
                    "\"responseMessage\":{\"itsResult\":\"ERROR\",\"itsMessage\":\"user not saved\"}" + 
                "}", 
                fakeResponse().getContentAsString());   
    }
    
    
    @SuppressWarnings("serial")
    private class SaveNewAhtvUserServletForTest extends SaveNewAhtvUserServlet
    {
        private CaptchaValidator itsCaptchaValidator;
        private AhtvUserServiceFacade itsUserServiceFacade;

        public SaveNewAhtvUserServletForTest(CaptchaValidator aCaptchaValidator, AhtvUserServiceFacade ahtvUserServiceFacade)
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
