package it.matrix.alicehometv;

import it.telecomitalia.rossoalice.aa.types.user.UserTypeSunrise;

import org.apache.commons.lang.builder.*;

public class UserProfile
{
    private String itsSunrisePassportCode;
    private String itsName;
    private String itsSurname;
    private String itsAddress;
    private String itsCity;
    private String itsZipCode;
    private String itsCLI;
    private String itsEmail;
    private String itsUsername;
    private String itsMobilePhoneNumber;
    private Integer itsUserType;

    public String name()
    {
        return itsName;
    }

    public void name(String aName)
    {
        itsName = aName;
    }

    public String surname()
    {
        return itsSurname;
    }

    public void surname(String aSurname)
    {
        itsSurname = aSurname;
    }

    public String cli()
    {
        return itsCLI;
    }

    public void cli(String aCli)
    {
        itsCLI = aCli;
    }

    public String username()
    {
        return itsUsername;
    }

    public void username(String aUsername)
    {
        itsUsername = aUsername;
    }

    public String mobilePhoneNumber()
    {
        return itsMobilePhoneNumber;
    }

    public void mobilePhoneNumber(String aMobilePhoneNumber)
    {
        itsMobilePhoneNumber = aMobilePhoneNumber;
    }

    public String email()
    {
        return itsEmail;
    }

    public void email(String aEmail)
    {
        itsEmail = aEmail;
    }

    public String address()
    {
        return itsAddress;
    }

    public void address(String aAddress)
    {
        itsAddress = aAddress;
    }

    public String city()
    {
        return itsCity;
    }

    public void city(String aCity)
    {
        itsCity = aCity;
    }

    public String zipCode()
    {
        return itsZipCode;
    }

    public void zipCode(String aZipCode)
    {
        itsZipCode = aZipCode;
    }

    public Integer type()
    {
        return itsUserType;
    }

    public String typeAsString()
    {
        return type() == null ? null : type().toString();
    }

    public void type(Integer aUserType)
    {
        itsUserType = aUserType;
    }

    public boolean isGold()
    {
        return type().intValue() == UserTypeSunrise.GOLD;
    }

    public boolean isGoldConvergente()
    {
        return type().intValue() == UserTypeSunrise.SUNRISE;
    }

    public String sunrisePassportCode()
    {
        return itsSunrisePassportCode;
    }
    
    public void sunrisePassportCode(String aSunrisePassportCode)
    {
        itsSunrisePassportCode = aSunrisePassportCode;
    }

    public boolean equals(Object anotherObject)
    {
        return EqualsBuilder.reflectionEquals(this, anotherObject);
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
