package it.matrix.alicehometv.servlet.test;

import static it.matrix.alicehometv.servlet.test.IPTVProfileCreator.*;
import static it.matrix.alicehometv.test.AhtvUserCreator.*;
import static it.matrix.alicehometv.test.UserProfileCreator.*;
import static org.fest.assertions.Assertions.*;
import it.matrix.alicehometv.AhtvUser;
import it.matrix.alicehometv.profile.iptv.IPTVUserProfileBroker;
import it.matrix.alicehometv.profile.iptv.NullIPTVProfile;
import it.matrix.alicehometv.service.AhtvUserService;
import it.matrix.alicehometv.service.UserAuthenticationService;
import it.matrix.alicehometv.servlet.UserDataServlet;
import it.matrix.alicehometv.test.AhtvUserBuilder;
import it.matrix.alicehometv.test.UserProfileCreator;
import it.telecomitalia.rossoalice.aa.sandbox.constants.Parameters;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestUserDataServlet extends ServletTestFixture
{
    private Mockery context = new JUnit4Mockery();

    private UserAuthenticationService mockUserAuthenticationService;
    private AhtvUserService mockAhtvUserService;
    private IPTVUserProfileBroker mockIPTVUserProfileBroker;

    @Before
    public void setUp()
    {
        mockUserAuthenticationService = context.mock(UserAuthenticationService.class);
        mockAhtvUserService = context.mock(AhtvUserService.class);
        mockIPTVUserProfileBroker = context.mock(IPTVUserProfileBroker.class);
        
        ServletTestFixtureUtils.setCookieWithAhtvUserIn(fakeRequest());
    }

    @Test
    public void shouldReturnUserDataAsJson() throws Exception
    {
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "anySPC");
        
        final AhtvUser ahtvUser = new AhtvUserBuilder(123, "anySPC").withAhtvEmail("mrossi@hotmail.com").withAhtvMobileNumber("+3932911111111").build();
        
        context.checking(new Expectations()
        {{      
            one(mockUserAuthenticationService).userProfileFor("anySPC"); will(returnValue(userProfileWith("Mario", "Rossi", "mr@alice.it", "0283587283")));
            one(mockAhtvUserService).findUserBySPC("anySPC"); will(returnValue(ahtvUser));
            one(mockIPTVUserProfileBroker).finderForCli("0283587283"); will(returnValue(iptvProfileWith("PURCHASE9999999", "PIN123456", new Integer(2))));
        }});
        
        new UserDataServletForTest(mockUserAuthenticationService, mockAhtvUserService, mockIPTVUserProfileBroker).doGet(fakeRequest(), fakeResponse());

        assertThat(fakeResponse().getContentAsString()).isEqualTo(
                "{" +
                    "\"compositeUserProfile\":" +
                    "{" +
                        "\"userProfile\":" +
                        "{" +
                            "\"itsName\":\"Mario\"," +
                            "\"itsSurname\":\"Rossi\"," +
                            "\"itsCLI\":\"0283587283\"," +
                            "\"itsEmail\":\"mr@alice.it\"" +
                         "}," +
                        "\"ahtvUser\":" +
                        "{" +
                            "\"itsAhtvUserId\":\"123\"," +
                            "\"itsSunrisePassportCode\":\"anySPC\"," +
                            "\"itsAhtvMobileNumber\":\"+3932911111111\"," +
                            "\"itsAhtvEmailAddress\":\"mrossi@hotmail.com\"," +
                            "\"isAhtvEmailCertified\":\"false\"," +
                            "\"isAhtvMobileCertified\":\"false\"" + 
                         "}," +
                         "\"iptvProfile\":" +
                         "{" +
                             "\"purchasePin\":\"PURCHASE9999999\"," +
                             "\"pcPin\":\"PIN123456\"," +
                             "\"pcLevel\":\"2\"" +
                         "}" +
                     "}" +
                 "}"
        		);
    }
   
    @Test
    public void shouldReturnPartialUserDataWithErrorMessageForIPTVProfileWhenCannotGetIPTVProfileData() throws Exception
    {
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "anySPC");
        
        context.checking(new Expectations()
        {{      
            one(mockUserAuthenticationService).userProfileFor("anySPC"); will(returnValue(userProfileWith("Mario", "Rossi", "mr@alice.it", "0283587283")));
            one(mockAhtvUserService).findUserBySPC("anySPC"); will(returnValue(new AhtvUserBuilder(123, "anySPC").build()));
            one(mockIPTVUserProfileBroker).finderForCli("0283587283"); will(returnValue(new NullIPTVProfile()));
        }});
        
        new UserDataServletForTest(mockUserAuthenticationService, mockAhtvUserService, mockIPTVUserProfileBroker).doGet(fakeRequest(), fakeResponse());

        assertThat(fakeResponse().getContentAsString()).contains
        (
            "\"serviceResponse\":" +
            "{" +
                "\"itsResult\":\"IPTV PROFILE ERROR\"," +
                "\"itsMessage\":\"Cannot get IPTVProfile\"" +
            "}"
        );
    }
    
    @Test
    public void shouldReturnAnErrorMessageWhenUserIsUnknown() throws Exception
    {
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, "anySPC");
        
        context.checking(new Expectations()
        {{      
            one(mockUserAuthenticationService).userProfileFor("anySPC"); will(returnValue(UserProfileCreator.goldUserWithUsername("m.rossi")));
            one(mockAhtvUserService).findUserBySPC("anySPC"); will(returnValue(unknownAhtvUser()));
            ignoring(mockIPTVUserProfileBroker);
        }});
        
        new UserDataServletForTest(mockUserAuthenticationService, mockAhtvUserService, mockIPTVUserProfileBroker).doGet(fakeRequest(), fakeResponse());

        assertThat(fakeResponse().getContentAsString()).isEqualTo
        (
            "{" +
                "\"responseMessage\":{\"itsResult\":\"ERROR\",\"itsMessage\":\"unknown ahtv user with SPC:anySPC\"}" + 
             "}"
        );
    }
   
    @SuppressWarnings("serial")
    private class UserDataServletForTest extends UserDataServlet
    {
        private UserAuthenticationService itsUserAuthenticationService;
        private AhtvUserService itsAhtvUserService;
        private IPTVUserProfileBroker itsIptvUserProfileBroker;

        public UserDataServletForTest(UserAuthenticationService userAuthenticationService, AhtvUserService ahtvUserService, IPTVUserProfileBroker iptvUserProfileBroker)
        {
            itsUserAuthenticationService = userAuthenticationService;
            itsAhtvUserService = ahtvUserService;
            itsIptvUserProfileBroker = iptvUserProfileBroker;
        }

        @Override
        protected UserAuthenticationService userAuthenticationService()
        {
            return itsUserAuthenticationService;
        }
        
        protected AhtvUserService ahtvUserService() 
        {
            return itsAhtvUserService;
        }
        
        protected IPTVUserProfileBroker iptvUserProfileBroker()
        {
            return itsIptvUserProfileBroker;
        }
    }
}
