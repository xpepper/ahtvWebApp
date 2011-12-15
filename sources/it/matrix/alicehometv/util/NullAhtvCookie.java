package it.matrix.alicehometv.util;

public class NullAhtvCookie extends AhtvCookie
{
    public NullAhtvCookie()
    {
    }
    
    public boolean isIptvUser()
    {
        return false;
    }

    public String clearedValue()
    {
        return "not detected";
    }

    public String get(String aKey)
    {
        return null;
    }

    public void put(String aKey, String aValue)
    {
        throw new UnsupportedOperationException();
    }
    
    public boolean isExpiredFor(String aGivenSunriseSessionId)
    {
        return true;
    }
}
