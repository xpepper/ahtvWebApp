package it.matrix.alicehometv.servlet.test;

import static it.matrix.alicehometv.test.AhtvUserCreator.*;
import static org.junit.Assert.*;
import it.matrix.alicehometv.AhtvUser;
import it.matrix.alicehometv.service.AhtvUserService;
import it.matrix.alicehometv.servlet.UserContactDataServlet;
import it.telecomitalia.rossoalice.aa.sandbox.constants.Parameters;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestUserContactDataServlet extends ServletTestFixture
{
    private Mockery context = new JUnit4Mockery();

    private AhtvUserService mockAhtvUserService;

    @Before
    public void setUp()
    {
        mockAhtvUserService = context.mock(AhtvUserService.class);
        
        ServletTestFixtureUtils.setCookieWithAhtvUserIn(fakeRequest());
    }

    @Test
    public void shouldReturnUserDataAsJson() throws Exception
    {
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "anySunrisePassportCode");
       
        final AhtvUser ahtvUser = ahtvUserWithId(123);
        ahtvUser.ahtvEmail("mrossi@hotmail.com");
        ahtvUser.certifyAhtvEMail(true);
        ahtvUser.ahtvMobileNumber("+3932911111111");
        ahtvUser.certifyAhtvMobile(false);
        
        context.checking(new Expectations()
        {{      
            one(mockAhtvUserService).findUserBySPC("anySunrisePassportCode"); will(returnValue(ahtvUser));
        }});
        
        new UserContactDataServletForTest(mockAhtvUserService).doGet(fakeRequest(), fakeResponse());

        assertEquals(
                "{" +
                    "\"ahtvUser\":" +
                    "{" +
                        "\"itsAhtvUserId\":\"123\"," +
                        "\"itsSunrisePassportCode\":\"anySunrisePassportCode\"," +
                        "\"itsAhtvMobileNumber\":\"+3932911111111\"," +
                        "\"itsAhtvEmailAddress\":\"mrossi@hotmail.com\"," +
                        "\"isAhtvEmailCertified\":\"true\"," +
                        "\"isAhtvMobileCertified\":\"false\"" + 
                     "}" +
                 "}", 
        		fakeResponse().getContentAsString());
    }
   
  
    @Test
    public void shouldReturnAnErrorMessageWhenUserIsUnknown() throws Exception
    {
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "anySunrisePassportCode");
        
        context.checking(new Expectations()
        {{      
            one(mockAhtvUserService).findUserBySPC("anySunrisePassportCode"); will(returnValue(unknownAhtvUser()));
        }});
        
        new UserContactDataServletForTest(mockAhtvUserService).doGet(fakeRequest(), fakeResponse());

        assertEquals(
                "{" +
                    "\"responseMessage\":{\"itsResult\":\"ERROR\",\"itsMessage\":\"unknown ahtv user with SPC:anySunrisePassportCode\"}" + 
                 "}", 
                fakeResponse().getContentAsString());
    }
   
    @SuppressWarnings("serial")
    private class UserContactDataServletForTest extends UserContactDataServlet
    {
        private AhtvUserService itsUserService;

        public UserContactDataServletForTest(AhtvUserService ahtvUserService)
        {
            itsUserService = ahtvUserService;
        }

        protected AhtvUserService ahtvUserService() 
        {
            return itsUserService;
        }
    }
}
