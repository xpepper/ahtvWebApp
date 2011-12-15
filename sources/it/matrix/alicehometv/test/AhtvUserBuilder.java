package it.matrix.alicehometv.test;

import it.matrix.alicehometv.AhtvUser;

public class AhtvUserBuilder
{
    private AhtvUser itsBuiltAhtvUser;

    public AhtvUserBuilder(int ahtvId)
    {
        itsBuiltAhtvUser = new AhtvUser(ahtvId, "anySunrisePassportCode");
    }

    public AhtvUserBuilder(int ahtvId, String spc)
    {
        itsBuiltAhtvUser = new AhtvUser(ahtvId, spc);
    }

    public AhtvUserBuilder withAhtvEmail(String email)
    {
        itsBuiltAhtvUser.ahtvEmail(email);
        return this;
    }

    public AhtvUserBuilder withAhtvMobileNumber(String mobileNumber)
    {
        itsBuiltAhtvUser.ahtvMobileNumber(mobileNumber);
        return this;
    }
    
    public AhtvUserBuilder withOriginalEmail(String email)
    {
        itsBuiltAhtvUser.originalEmail(email);
        return this;
    }

    public AhtvUserBuilder withOriginalMobile(String mobileNumber)
    {
        itsBuiltAhtvUser.originalMobileNumber(mobileNumber);
        return this;
    }
    
    public AhtvUser build()
    {
        return itsBuiltAhtvUser;
    }
}
