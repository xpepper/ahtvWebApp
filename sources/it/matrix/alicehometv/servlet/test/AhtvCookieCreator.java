package it.matrix.alicehometv.servlet.test;

import it.matrix.alicehometv.util.AhtvCookie;

public class AhtvCookieCreator
{
    public static AhtvCookie withIptvUserFlag(boolean isIptvUser)
    {
        AhtvCookie ahtvCookie = new AhtvCookie();
        ahtvCookie.put("isIptvUser", Boolean.toString(isIptvUser));
        return ahtvCookie;
    }

    public static AhtvCookie withSunrisePassportCode(String aSunrisePassportCode)
    {
        AhtvCookie ahtvCookie = new AhtvCookie();
        ahtvCookie.put("sunrisePassportCode", aSunrisePassportCode);
        return ahtvCookie;
    }
    
    public static AhtvCookie withSunriseSessionId(String currentSunriseSessionId)
    {
        AhtvCookie ahtvCookie = new AhtvCookie();
        ahtvCookie.put("sunriseSessionId", currentSunriseSessionId);
        return ahtvCookie;
    }
}
