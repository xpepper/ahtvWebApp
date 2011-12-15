package it.matrix.alicehometv.util;

import it.matrix.alicehometv.logger.ActivityLogger;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

public class AhtvCookie extends Cookie
{
    public static final String NAME = "ahtv";

    public static final String USERNAME_KEY = "userName";
    public static final String EMAIL_KEY = "email";
    public static final String IPTV_USER_FLAG_KEY = "isIptvUser";
    public static final String USER_PROFILE_TYPE_KEY = "userType";
    public static final String CLI_KEY = "cli";
    public static final String SUNRISE_SESSION_ID_KEY = "sunriseSessionId";

    private static final String SUNRISE_PASSPORT_CODE = "sunrisePassportCode";
    private static final String AHTV_USER_ID_KEY = "ahtvId";



    public AhtvCookie()
    {
        super(NAME, new StringStorer().asEncryptedString());
    }

    public AhtvCookie(Cookie aCookie)
    {
        this();

        setComment(aCookie.getComment());
        setMaxAge(aCookie.getMaxAge());
        setPath(aCookie.getPath());
        setSecure(aCookie.getSecure());
        setValue(aCookie.getValue());
        setVersion(aCookie.getVersion());

        if (aCookie.getDomain() != null)
            setDomain(aCookie.getDomain());
    }

    public String get(String aKey)
    {
        StringStorer storer = StringStorerBuilder.buildFromEncrypted(getValue());
        return storer.get(aKey);
    }

    public void put(String aKey, String aValue)
    {
        if (aValue != null)
        {
            StringStorer storer = StringStorerBuilder.buildFromEncrypted(getValue());
            storer.put(aKey, aValue);
            setValue(storer.asEncryptedString());
        }
    }

    public String clearedValue()
    {
        return StringStorerBuilder.buildFromEncrypted(getValue()).toString();
    }

    public boolean isIptvUser()
    {
        return Boolean.parseBoolean(get(IPTV_USER_FLAG_KEY));
    }

    public String sunrisePassportCode()
    {
        return get(SUNRISE_PASSPORT_CODE);
    }

    public void sunrisePassportCode(String aSunrisePassportCode)
    {
        put(SUNRISE_PASSPORT_CODE, aSunrisePassportCode);
    }
    
    public Integer ahtvUserId()
    {
        try
        {
            return new Integer(get(AHTV_USER_ID_KEY));
        }
        catch (NumberFormatException e)
        {
            ActivityLogger.warning("Cannot get " + AHTV_USER_ID_KEY + " from ahtv cookie");
            return null;
        }
    }

    public void addAhtvUserId(int ahtvId)
    {
        put(AHTV_USER_ID_KEY, Integer.toString(ahtvId));
    }

    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean hasNoAhtvUserId()
    {
        return ahtvUserId() == null;
    }
    
    public boolean isExpiredFor(String aGivenSunriseSessionId)
    {
        String actualSunriseSessionId = get(SUNRISE_SESSION_ID_KEY);
        return !StringUtils.equals(actualSunriseSessionId, aGivenSunriseSessionId);
    }

}
