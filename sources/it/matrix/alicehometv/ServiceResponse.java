package it.matrix.alicehometv;

import org.apache.commons.lang.builder.*;

@SuppressWarnings("unused")
public class ServiceResponse
{
    private static final String ERROR_RESULT = "ERROR";
    private static final String OK_RESULT = "OK";
    
    private final String itsResult;
    private final String itsMessage;

    private ServiceResponse(String result, String message)
    {
        itsResult = result;
        itsMessage = message;
    }

    public static ServiceResponse okResponseWith(String aMessage)
    {
        return responseWith(OK_RESULT, aMessage);
    }

    public static ServiceResponse errorResponseWith(String aMessage)
    {
        return responseWith(ERROR_RESULT, aMessage);
    }

    public static ServiceResponse iptvErrorResponse()
    {
        return responseWith("IPTV PROFILE ERROR", "Cannot get IPTVProfile");
    }

    private static ServiceResponse responseWith(String aResult, String aMessage)
    {
        return new ServiceResponse(aResult, aMessage);
    }

    public boolean isOk()
    {
        return OK_RESULT.equals(itsResult);
    }

    public String message()
    {
        return itsMessage;
    }
    
    public boolean equals(Object aObj)
    {
        return EqualsBuilder.reflectionEquals(this, aObj);
    }

    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
    
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
