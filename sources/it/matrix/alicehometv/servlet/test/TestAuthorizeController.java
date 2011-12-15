package it.matrix.alicehometv.servlet.test;

import static it.matrix.alicehometv.servlet.test.ServletTestFixtureUtils.*;
import static it.matrix.alicehometv.test.AhtvUserCreator.*;
import static it.matrix.alicehometv.test.UserProfileCreator.*;
import static it.matrix.alicehometv.util.test.AssertUtils.*;
import static org.junit.Assert.*;
import it.matrix.alicehometv.UserProfile;
import it.matrix.alicehometv.service.*;
import it.matrix.alicehometv.servlet.AuthorizeController;
import it.matrix.alicehometv.util.AhtvCookie;
import it.telecomitalia.rossoalice.aa.sandbox.constants.Parameters;

import java.net.MalformedURLException;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestAuthorizeController extends ServletTestFixture
{
    private Mockery context = new JUnit4Mockery();

    private UserAuthenticationService mockUserAuthenticationService;
    private AhtvUserService mockAhtvUserService;
    private AhtvUserServiceFacade mockAhtvUserServiceFacade;

    @Before
    public void setUp()
    {
        mockUserAuthenticationService = context.mock(UserAuthenticationService.class);
        mockAhtvUserService = context.mock(AhtvUserService.class);
        mockAhtvUserServiceFacade = context.mock(AhtvUserServiceFacade.class);
    }

    @Test
    public void shouldForwardToLoggedSection() throws Exception
    {
        setSunrisePassportCodeAttribute("anySunrisePassportCode");
        setCookieWithAhtvUserIn(fakeRequest());
        
        context.checking(new Expectations()
        {{            
            one(mockAhtvUserService).findUserBySPC("anySunrisePassportCode");  will(returnValue(ahtvUserWithId(123)));
        }});

        new AuthorizeControllerForTest(mockUserAuthenticationService, mockAhtvUserService, mockAhtvUserServiceFacade).doGet(fakeRequest(), fakeResponse());

        assertEquals("/templates/logged.jsp", fakeResponse().getForwardedUrl());
    }
    
    @Test
    public void ahtvCookieShouldBeCreatedWhenNotPresent() throws Exception
    {
        setNoCookiesIn(fakeRequest());
        setSunrisePassportCodeAttribute("aSunrisePassportCode");
        setNewSunriseSessionIdAttribute("1234567890");

        final UserProfile userProfile = goldUserWithUsername("mario_rossi");
        userProfile.cli("028358761");
        
        context.checking(new Expectations()
        {{                
            one(mockUserAuthenticationService).userProfileFor("aSunrisePassportCode"); will(returnValue(userProfile));
            one(mockUserAuthenticationService).isIptvUser("aSunrisePassportCode"); will(returnValue(false));
            ignoring(mockAhtvUserServiceFacade);
            allowing(mockAhtvUserService).findUserBySPC("aSunrisePassportCode");  will(returnValue(unknownAhtvUser()));
        }});
        
        assertNull(fakeResponse().getCookie(AhtvCookie.NAME));

        new AuthorizeControllerForTest(mockUserAuthenticationService, mockAhtvUserService, mockAhtvUserServiceFacade).doGet(fakeRequest(), fakeResponse());

        AhtvCookie ahtvCookie = new AhtvCookie(fakeResponse().getCookie(AhtvCookie.NAME));
        assertEquals("Should contain the user email", "mario_rossi@alice.it", ahtvCookie.get("email"));
        assertEquals("Should contain the username", "mario_rossi", ahtvCookie.get("userName"));
        assertFalse("Should contain the user iptv flag", ahtvCookie.isIptvUser());
        assertEquals("Should contain the user type", "2", ahtvCookie.get("userType"));
        assertEquals("Should contain the CLI number", "028358761", ahtvCookie.get("cli"));
        assertEquals("Should contain the Sunrise Passport Code", "aSunrisePassportCode", ahtvCookie.get("sunrisePassportCode"));
        assertEquals("Should contain the sunrise cookie id", "1234567890", ahtvCookie.get("sunriseSessionId"));
    }

    @Test
    @SuppressWarnings("unused")
    public void ahtvCookieShouldBeRecreatedWhenSunriseSessionIdIsChanged() throws Exception
    {
        setCookieWithSunriseSessionId(fakeRequest(), "thePreviousSunriseSessionId");
        setSunrisePassportCodeAttribute("anySunrisePassportCode");
        setNewSunriseSessionIdAttribute("theNewSunriseSessionId");

        context.checking(new Expectations()
        {{                
            one(mockUserAuthenticationService).userProfileFor("anySunrisePassportCode"); will(returnValue(goldConvergenteUserWith("pdb@alice.it", "34812345678")));
            ignoring(mockUserAuthenticationService).isIptvUser(with(any(String.class)));
            ignoring(mockAhtvUserServiceFacade);
            allowing(mockAhtvUserService).findUserBySPC("anySunrisePassportCode");  will(returnValue(unknownAhtvUser()));
        }});
        
        new AuthorizeControllerForTest(mockUserAuthenticationService, mockAhtvUserService, mockAhtvUserServiceFacade).doGet(fakeRequest(), fakeResponse());

        AhtvCookie ahtvCookie = new AhtvCookie(fakeResponse().getCookie(AhtvCookie.NAME));
        assertEquals("Should contain the user email", "pdb@alice.it", ahtvCookie.get("email"));
        assertFalse("Should contain the user ahtv flag", ahtvCookie.isIptvUser());
        assertEquals("Should contain the user type", "1", ahtvCookie.get("userType"));
        assertEquals("Should contain the Sunrise Passport Code", "anySunrisePassportCode", ahtvCookie.get("sunrisePassportCode"));
        assertEquals("The sunrise cookie id should be changed", "theNewSunriseSessionId", ahtvCookie.get("sunriseSessionId"));
    }
    
    @Test
    public void shouldCheckForSunriseProfileDataUpdate() throws Exception
    {
        setNewSunriseSessionIdAttribute("theNewSunriseSessionId");
        setSunrisePassportCodeAttribute("anySunrisePassportCode");
        
        AhtvCookie existingAhtvCookie = new AhtvCookie();
        existingAhtvCookie.put("sunriseSessionId", "thePreviousSunriseSessionId");
        fakeRequest().setCookies(new Cookie[] { existingAhtvCookie });
        
        final UserProfile userProfile = goldConvergenteUserWith("pbianchi@alice.it", "34812345678");

        context.checking(new Expectations()
        {{                
            one(mockUserAuthenticationService).userProfileFor("anySunrisePassportCode"); will(returnValue(userProfile));
            ignoring(mockUserAuthenticationService).isIptvUser(with(any(String.class)));
            one(mockAhtvUserServiceFacade).checkForSunriseProfileDataUpdateFor(userProfile); 
            allowing(mockAhtvUserService).findUserBySPC("anySunrisePassportCode");  will(returnValue(unknownAhtvUser()));
        }});
        
        new AuthorizeControllerForTest(mockUserAuthenticationService, mockAhtvUserService, mockAhtvUserServiceFacade).doGet(fakeRequest(), fakeResponse());
    }
    
    @Test
    public void shouldSetSomeAttributesForLoggedSection() throws Exception
    {
        setSunrisePassportCodeAttribute("anySunrisePassportCode");
        AhtvCookie ahtvCookie = new AhtvCookie();
        ahtvCookie.put("userName", "mario_rossi");
        ahtvCookie.put("isIptvUser", Boolean.toString(true));
        ahtvCookie.put("userType", "2");
        fakeRequest().setCookies(new Cookie[] { ahtvCookie });

        context.checking(new Expectations()
        {{
            one(mockAhtvUserService).findUserBySPC("anySunrisePassportCode"); will(returnValue(ahtvUserWithId(123)));
        }});

        new AuthorizeControllerForTest(mockUserAuthenticationService, mockAhtvUserService, mockAhtvUserServiceFacade).doGet(fakeRequest(), fakeResponse());

        assertEquals("mario_rossi", fakeRequest().getAttribute("userName"));
        assertEquals(Boolean.TRUE, fakeRequest().getAttribute("isIptvUser"));
        assertEquals(Boolean.FALSE, fakeRequest().getAttribute("isNewUser"));
        assertEquals(Boolean.FALSE, fakeRequest().getAttribute("hasPendingData"));
        assertEquals("2", fakeRequest().getAttribute("userProfileType")); 
    }
    
    @Test
    public void firstLoginAttributeShouldBeSetToTrueWhenAhtvCookieIsCreated() throws Exception
    {
        setSunrisePassportCodeAttribute("anySunrisePassportCode");
        setNewSunriseSessionIdAttribute("1234567890");
        setNoCookiesIn(fakeRequest());

        context.checking(new Expectations()
        {{                
            one(mockUserAuthenticationService).userProfileFor("anySunrisePassportCode"); will(returnValue(goldUserWithUsername("mario_rossi")));
            one(mockUserAuthenticationService).isIptvUser("anySunrisePassportCode"); will(returnValue(false));
            ignoring(mockAhtvUserServiceFacade);
            allowing(mockAhtvUserService).findUserBySPC("anySunrisePassportCode"); will(returnValue(unknownAhtvUser()));
        }});

        new AuthorizeControllerForTest(mockUserAuthenticationService, mockAhtvUserService, mockAhtvUserServiceFacade).doGet(fakeRequest(), fakeResponse());

        assertEquals(Boolean.TRUE, fakeRequest().getAttribute("isFirstLogin"));
    }
    
    @Test
    public void firstLoginAttributeShouldBeSetToFalseWhenAhtvCookieExists() throws Exception
    {
        setSunrisePassportCodeAttribute("anySunrisePassportCode");
        setCookieWithAhtvUserIn(fakeRequest());

        context.checking(new Expectations()
        {{                
            allowing(mockUserAuthenticationService).userProfileFor("anySunrisePassportCode"); will(returnValue(goldUserWithUsername("mario_rossi")));
            allowing(mockUserAuthenticationService).isIptvUser("anySunrisePassportCode"); will(returnValue(false));
            ignoring(mockAhtvUserServiceFacade);
            allowing(mockAhtvUserService).findUserBySPC("anySunrisePassportCode"); will(returnValue(unknownAhtvUser()));
        }});

        new AuthorizeControllerForTest(mockUserAuthenticationService, mockAhtvUserService, mockAhtvUserServiceFacade).doGet(fakeRequest(), fakeResponse());

        assertEquals(Boolean.FALSE, fakeRequest().getAttribute("isFirstLogin"));
    }
    
    @Test
    public void shouldNotAddAhtvUserIdToAhtvCookieWhenUserIsUnknown() throws Exception
    {
        setSunrisePassportCodeAttribute("anySunrisePassportCode");
        setNoCookiesIn(fakeRequest());
        
        context.checking(new Expectations()
        {{                
            allowing(mockUserAuthenticationService).userProfileFor("anySunrisePassportCode"); will(returnValue(goldUserWithUsername("anyMail")));
            ignoring(mockUserAuthenticationService).isIptvUser(with(any(String.class)));
            ignoring(mockAhtvUserServiceFacade);
            one(mockAhtvUserService).findUserBySPC("anySunrisePassportCode"); will(returnValue(unknownAhtvUser()));
        }});
        
        new AuthorizeControllerForTest(mockUserAuthenticationService, mockAhtvUserService, mockAhtvUserServiceFacade).doGet(fakeRequest(), fakeResponse());
    
        Cookie cookie = fakeResponse().getCookie(AhtvCookie.NAME);
        assertNull("Should NOT contain any ahtv id", new AhtvCookie(cookie).ahtvUserId());
    }

    @Test
    public void shouldAddAhtvUserIdToAhtvCookieWhenUserExists() throws Exception
    {
        setSunrisePassportCodeAttribute("anySunrisePassportCode");
        setNoCookiesIn(fakeRequest());
    
        context.checking(new Expectations()
        {{                
            allowing(mockUserAuthenticationService).userProfileFor("anySunrisePassportCode"); will(returnValue(goldUserWithUsername("anyMail")));
            ignoring(mockUserAuthenticationService).isIptvUser(with(any(String.class)));
            ignoring(mockAhtvUserServiceFacade);
            one(mockAhtvUserService).findUserBySPC("anySunrisePassportCode"); will(returnValue(ahtvUserWithId(123)));
        }});
        
        new AuthorizeControllerForTest(mockUserAuthenticationService, mockAhtvUserService, mockAhtvUserServiceFacade).doGet(fakeRequest(), fakeResponse());
    
        Cookie cookie = fakeResponse().getCookie(AhtvCookie.NAME);
        assertEquals("Should contain the ahtv id", new Integer("123"), new AhtvCookie(cookie).ahtvUserId());
    }

    @Test
    public void ahtvCookieCreationShouldNotFailWhenUserProfileHasEmptyValues() throws Exception
    {
        setSunrisePassportCodeAttribute("anySunrisePassportCode");
        setNoCookiesIn(fakeRequest());
        
        final UserProfile userProfileWithEmptyValues = new UserProfile();
        userProfileWithEmptyValues.email(StringUtils.EMPTY);
        userProfileWithEmptyValues.cli(null);
        
        context.checking(new Expectations()
        {{                
            one(mockUserAuthenticationService).userProfileFor("anySunrisePassportCode"); will(returnValue(userProfileWithEmptyValues));
            allowing(mockUserAuthenticationService).isIptvUser(with(any(String.class)));
            ignoring(mockAhtvUserServiceFacade);
            allowing(mockAhtvUserService).findUserBySPC("anySunrisePassportCode"); will(returnValue(unknownAhtvUser()));
        }});
        
        new AuthorizeControllerForTest(mockUserAuthenticationService, mockAhtvUserService, mockAhtvUserServiceFacade).doGet(fakeRequest(), fakeResponse());

        Cookie cookie = fakeResponse().getCookie(AhtvCookie.NAME);

        AhtvCookie ahtvCookie = new AhtvCookie(cookie);
        assertEmpty(ahtvCookie.get("email"));
        assertNull(ahtvCookie.get("userType"));
        assertNull(ahtvCookie.get("cli"));
    }
    
    private void setSunrisePassportCodeAttribute(String spc)
    {
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC, spc);
    }
    
    private void setNewSunriseSessionIdAttribute(String newSunriseSessionId)
    {
        fakeRequest().setAttribute(Parameters.AASANDBOX_SPC_TOKEN, newSunriseSessionId);
    }

    @SuppressWarnings("serial")
    private class AuthorizeControllerForTest extends AuthorizeController
    {
        private UserAuthenticationService itsUserAuthenticationService;
        private AhtvUserService itsAhtvUserService;
        private AhtvUserServiceFacade itsAhtvUserServiceFacade;

        public AuthorizeControllerForTest(UserAuthenticationService userAuthenticationService, AhtvUserService ahtvUserService, AhtvUserServiceFacade ahtvUserServiceFacade)
        {
            itsUserAuthenticationService = userAuthenticationService;
            itsAhtvUserService = ahtvUserService;
            itsAhtvUserServiceFacade = ahtvUserServiceFacade;
        }

        @Override
        protected AhtvUserService ahtvUserService()
        {
            return itsAhtvUserService;
        }
        
        @Override
        protected UserAuthenticationService userAuthenticationService()
        {
            return itsUserAuthenticationService;
        }
        
        @Override
        protected AhtvUserServiceFacade ahtvUserServiceFacade(AhtvUserService ahtvUserService, UserAuthenticationService userAuthenticationService) throws MalformedURLException
        {
            return itsAhtvUserServiceFacade;
        }
    }
}
