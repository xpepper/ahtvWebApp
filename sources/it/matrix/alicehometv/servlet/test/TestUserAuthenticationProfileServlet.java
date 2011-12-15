package it.matrix.alicehometv.servlet.test;

import static it.matrix.alicehometv.test.UserProfileCreator.*;
import static org.junit.Assert.*;
import it.matrix.alicehometv.service.UserAuthenticationService;
import it.matrix.alicehometv.servlet.UserAuthenticationProfileServlet;
import it.telecomitalia.rossoalice.aa.sandbox.constants.Parameters;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestUserAuthenticationProfileServlet extends ServletTestFixture
{
    private Mockery context = new JUnit4Mockery();

    private UserAuthenticationService mockUserAuthenticationService;

    @Before
    public void setUp()
    {
        mockUserAuthenticationService = context.mock(UserAuthenticationService.class);
    }

    @Test
    public void shouldReturnUserDataAsJson() throws Exception
    {
        ServletTestFixtureUtils.setCookieWithAhtvUserIn(fakeRequest());
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "anySPC");
        
        context.checking(new Expectations()
        {{      
            one(mockUserAuthenticationService).userProfileFor("anySPC"); will(returnValue(userProfileWith("Ugo", "Rossi", "urossi@alice.it", "0283587283")));
        }});
        
        new UserAuthenticationProfileServletForTest(mockUserAuthenticationService).doGet(fakeRequest(), fakeResponse());

        assertEquals(
                "{" +
                    "\"userProfile\":" +
                    "{" +
                        "\"itsName\":\"Ugo\"," +
                        "\"itsSurname\":\"Rossi\"," +
                        "\"itsCLI\":\"0283587283\"," +
                        "\"itsEmail\":\"urossi@alice.it\"" +
                     "}" +
                 "}", 
        		fakeResponse().getContentAsString());
    }
   
    
    
    @SuppressWarnings("serial")
    private class UserAuthenticationProfileServletForTest extends UserAuthenticationProfileServlet
    {
        private UserAuthenticationService itsUserAuthenticationService;

        public UserAuthenticationProfileServletForTest(UserAuthenticationService userAuthenticationService)
        {
            itsUserAuthenticationService = userAuthenticationService;
        }

        @Override
        protected UserAuthenticationService userAuthenticationService()
        {
            return itsUserAuthenticationService;
        }
    }
}
