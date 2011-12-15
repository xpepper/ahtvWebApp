package it.matrix.alicehometv.service;

import org.apache.commons.lang.builder.*;

public class OTPVerificationResult
{
    public static final OTPVerificationResult NULL_RESULT = new OTPVerificationResult(-1, "", false, false);
    
    private final int itsResultCode;
    private final String itsResultMessage;
    private final boolean isOTPVerified;
    private final boolean isOTPMatching;

    public OTPVerificationResult(int resultCode, String resultMessage, boolean isVerified, boolean isPasswordMatching)
    {
        itsResultCode = resultCode;
        itsResultMessage = resultMessage;
        isOTPVerified = isVerified;
        isOTPMatching = isPasswordMatching;
    }

    public boolean isOk()
    {
        return itsResultCode == 0;
    }
    
    public String message()
    {
        return itsResultMessage;
    }

    public boolean isControlCodeMatching()
    {
        return isOTPMatching;
    }
    
    public boolean isControlCodeVerified()
    {
        return isOTPVerified;
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
