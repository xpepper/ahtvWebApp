package it.matrix.alicehometv;

import static org.apache.commons.lang.StringUtils.*;
import it.matrix.services.userprivacydata.UserPDP;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.*;

public class AhtvUser
{
    private static final AhtvUser NULL_USER = new AhtvUser(0, StringUtils.EMPTY); 
    
    private int itsAhtvUserId;
    private String itsSunrisePassportCode;
    private String itsOriginalMobileNumber;
    private String itsAhtvMobileNumber;
    private String itsOriginalEmailAddress;
    private String itsAhtvEmailAddress;
    private boolean isAhtvEmailCertified;
    private boolean isAhtvMobileCertified;

    public AhtvUser(int ahtvUserId, String sunrisePassportCode)
    {
        itsAhtvUserId = ahtvUserId;
        itsSunrisePassportCode = sunrisePassportCode;
        
        itsOriginalMobileNumber = null;
        itsOriginalEmailAddress = null;

        //TODO passare ad un oggetto?
        itsAhtvMobileNumber = null;
        isAhtvMobileCertified = false;

        //TODO passare ad un oggetto?
        itsAhtvEmailAddress = null;        
        isAhtvEmailCertified = false;
    }

    public static AhtvUser from(UserPDP userPDP)
    {
        if (userPDP != null && userPDP.getUserId() != null && StringUtils.isNotEmpty(userPDP.getUserId().getExtUserId()))
            return new AhtvUser(userPDP.getUserId().getIntUserId(), userPDP.getUserId().getExtUserId());
        else
            return NULL_USER;
    }
    
    public boolean isUnknown()
    {
        return ahtvId() == 0;
    }
    
    public boolean exists()
    {
        return !isUnknown();
    }
    
    public String sunrisePassportCode()
    {
        return itsSunrisePassportCode;
    }
    
    public int ahtvId()
    {
        return itsAhtvUserId;
    }

    public void originalMobileNumber(String aMobileNumber)
    {
        itsOriginalMobileNumber = aMobileNumber;
    }

    public void ahtvMobileNumber(String aMobileNumber)
    {
        itsAhtvMobileNumber = aMobileNumber;
    }
    
    public boolean hasCertifiedAhtvMobile()
    {
        return isNotEmpty(ahtvMobileNumber()) && isAhtvMobileCertified;
    }

    public void certifyAhtvMobile(boolean certificationStatus)
    {
        isAhtvMobileCertified = isNotEmpty(ahtvMobileNumber()) && certificationStatus;
    }

    public String originalMobileNumber()
    {
        return itsOriginalMobileNumber;
    }

    public String ahtvMobileNumber()
    {
        return itsAhtvMobileNumber;
    }
    
    public void originalEmail(String anEmailAddress)
    {
        itsOriginalEmailAddress = anEmailAddress;
    }

    public String originalEmail()
    {
        return itsOriginalEmailAddress;
    }

    public void ahtvEmail(String anEmailAddress)
    {
        itsAhtvEmailAddress = anEmailAddress;
    }

    public boolean hasCertifiedAhtvEmail()
    {
        return isNotEmpty(ahtvEmail()) && isAhtvEmailCertified;
    }

    public void certifyAhtvEMail(boolean certificationStatus)
    {
        isAhtvEmailCertified = isNotEmpty(ahtvEmail()) && certificationStatus;
    }

    public boolean hasPendingData()
    {
        return hasAhtvEmailOrMobile() && !areAhtvMailAndMobileCertified();
    }

    public String ahtvEmail()
    {
        return itsAhtvEmailAddress;
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
    
    private boolean hasAhtvEmailOrMobile()
    {
        return isNotEmpty(ahtvEmail()) || isNotEmpty(ahtvMobileNumber());
    }

    private boolean areAhtvMailAndMobileCertified()
    {
        return hasCertifiedAhtvEmail() && hasCertifiedAhtvMobile();
    }
}
