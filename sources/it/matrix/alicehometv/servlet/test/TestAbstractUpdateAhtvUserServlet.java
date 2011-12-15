package it.matrix.alicehometv.servlet.test;

import it.matrix.alicehometv.captcha.CaptchaValidator;
import it.matrix.alicehometv.servlet.AbstractUpdateAhtvUserServlet;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.Assert.*;

@RunWith(JMock.class)
public class TestAbstractUpdateAhtvUserServlet extends ServletTestFixture 
{
    private Mockery context = new JUnit4Mockery();

    private CaptchaValidator mockCaptchaValidator;
    private MockHttpSession fakeHttpSession;

    @Before
    public void setUp()
    {
        mockCaptchaValidator = context.mock(CaptchaValidator.class);

        fakeHttpSession = new MockHttpSession();
        fakeRequest().setSession(fakeHttpSession);
        
        ServletTestFixtureUtils.setCookieWithAhtvUserIn(fakeRequest());
    }

    @Test
    public void shouldCheckCaptchaWhenMobileNumberIsProvided() throws Exception
    {
        fakeRequest().setParameter("mobileNumber", "+393484738746");
        fakeRequest().setParameter("captchaResponse", "m8TyiZ");
        
        context.checking(new Expectations()
        {{      
            one(mockCaptchaValidator).validate(fakeHttpSession.getId(), "m8TyiZ"); will(returnValue(true));
        }});
        
        new UpdateUserProfileServletForTest(mockCaptchaValidator).doPost(fakeRequest(), fakeResponse());
    }
    
    @Test
    public void shouldNotCheckCaptchaWhenMobileNumberIsNotProvided() throws Exception
    {
        fakeRequest().setParameter("mobileNumber", StringUtils.EMPTY);
        context.checking(new Expectations()
        {{      
            never(mockCaptchaValidator);
        }});
        
        new UpdateUserProfileServletForTest(mockCaptchaValidator).doPost(fakeRequest(), fakeResponse());
    }

    @Test
    public void shouldReturnAnErrorMessageWhenCaptchaIsIncorrect() throws Exception
    {
        fakeRequest().setParameter("mobileNumber", "+393484738746");
        fakeRequest().setParameter("captchaResponse", "m8TyiZ");
        
        context.checking(new Expectations()
        {{      
            one(mockCaptchaValidator).validate(fakeHttpSession.getId(), "m8TyiZ"); will(returnValue(false));
        }});
        
        new UpdateUserProfileServletForTest(mockCaptchaValidator).doPost(fakeRequest(), fakeResponse());
        
        assertEquals(
                "{" +
                    "\"responseMessage\":{\"itsResult\":\"ERROR\",\"itsMessage\":\"invalid captcha provided\"}" + 
                "}", 
                fakeResponse().getContentAsString());        
    }
    
    @SuppressWarnings("serial")
    private class UpdateUserProfileServletForTest extends AbstractUpdateAhtvUserServlet
    {
        private CaptchaValidator itsCaptchaValidator;

        public UpdateUserProfileServletForTest(CaptchaValidator aCaptchaValidator)
        {
            itsCaptchaValidator = aCaptchaValidator;
        }
        
        protected CaptchaValidator captchaValidator()
        {
            return itsCaptchaValidator;
        }

        protected boolean saveOrUpdateWith(String aEmailAddress, String aMobileNumber, HttpServletRequest aRequest, HttpServletResponse aResponse) throws MalformedURLException
        {
            return false;
        }
     }
}
