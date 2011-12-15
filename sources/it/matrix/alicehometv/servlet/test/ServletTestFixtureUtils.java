package it.matrix.alicehometv.servlet.test;

import it.matrix.alicehometv.util.AhtvCookie;

import javax.servlet.http.Cookie;

import org.springframework.mock.web.MockHttpServletRequest;

public class ServletTestFixtureUtils
{
    public static void setCookieWithAhtvUserIn(MockHttpServletRequest fakeRequest)
    {
        fakeRequest.setCookies(new Cookie[] { AhtvCookieCreator.withIptvUserFlag(true) });
    }

    public static void setCookieWithNonAhtvUserIn(MockHttpServletRequest fakeRequest)
    {
        fakeRequest.setCookies(new Cookie[] { AhtvCookieCreator.withIptvUserFlag(false) });
    }
    
    public static void setNoCookiesIn(MockHttpServletRequest fakeRequest)
    {
        fakeRequest.setCookies(new Cookie[0]);
    }

    public static void setCookieWithAhtvUserIn(int ahtvUserId, MockHttpServletRequest fakeRequest)
    {
        AhtvCookie ahtvCookie = AhtvCookieCreator.withIptvUserFlag(true);
        ahtvCookie.addAhtvUserId(ahtvUserId);
        fakeRequest.setCookies(new Cookie[] { ahtvCookie });
    }
    
    public static void setCookieWithCLI(String aCLI, MockHttpServletRequest fakeRequest)
    {
        AhtvCookie ahtvCookie = AhtvCookieCreator.withIptvUserFlag(true);
        ahtvCookie.put(AhtvCookie.CLI_KEY, aCLI);
        fakeRequest.setCookies(new Cookie[] { ahtvCookie });
    }
    
    public static void setCookieWithSunriseSessionId(MockHttpServletRequest fakeRequest, String currentSunriseSessionId)
    {
        fakeRequest.setCookies(new Cookie[] { AhtvCookieCreator.withSunriseSessionId(currentSunriseSessionId) });
    }
    
    public static void setCookieWithSunrisePassportCodeIn(String aSunrisePassportCode, MockHttpServletRequest aFakeRequest)
    {
        aFakeRequest.setCookies(new Cookie[] { AhtvCookieCreator.withSunrisePassportCode(aSunrisePassportCode) });
    }


}
