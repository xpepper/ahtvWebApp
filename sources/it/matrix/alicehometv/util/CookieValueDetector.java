package it.matrix.alicehometv.util;

import javax.servlet.http.Cookie;

public class CookieValueDetector
{
    private Cookie[] itsCookies;

    public CookieValueDetector(Cookie[] cookies)
    {
        itsCookies = cookies;
    }

    public String valueOf(String aCookieName)
    {
        Cookie detectedCookie = detect(aCookieName);
        return (detectedCookie != null) ? detectedCookie.getValue() : null;
    }

    public Cookie detect(String aCookieName)
    {
        Cookie detectedCookie = null;
        if (itsCookies != null)
        {
            for (int i = 0; i < itsCookies.length; i++)
            {
                if (itsCookies[i].getName().equals(aCookieName))
                {
                    detectedCookie = itsCookies[i];
                    break;
                }
            }
        }
        return detectedCookie;
    }

    public AhtvCookie detectAhtvCookie()
    {
        Cookie cookie = detect(AhtvCookie.NAME);
        return (cookie != null) ? new AhtvCookie(cookie) : new NullAhtvCookie();
    }

}
