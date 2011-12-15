package it.matrix.alicehometv.test;

import it.matrix.alicehometv.UserProfile;
import it.telecomitalia.rossoalice.aa.types.user.UserTypeSunrise;

public class UserProfileCreator
{
    public static UserProfile goldUserWithUsername(String userName)
    {
        UserProfile userProfile = goldUserWith(userName + "@alice.it");
        userProfile.username(userName);
        return userProfile;
    }
    
    public static UserProfile goldConvergenteUserWith(String emailAddress, String mobileNumber)
    {
        return goldConvergenteUserWith("anySPC", emailAddress, mobileNumber);
    }
    
    public static UserProfile goldConvergenteUserWith(String aSPC, String emailAddress, String mobileNumber)
    {
        UserProfile userProfile = new UserProfile();
        userProfile.sunrisePassportCode(aSPC);
        userProfile.type(UserTypeSunrise.SUNRISE); 
        userProfile.email(emailAddress);
        userProfile.mobilePhoneNumber(mobileNumber);
        return userProfile;
    }

    public static UserProfile userProfileWith(String name, String surname, String email, String cli)
    {
        UserProfile userProfile = new UserProfile();
        userProfile.name(name);
        userProfile.surname(surname);
        userProfile.email(email);
        userProfile.cli(cli);
        return userProfile;
    }

    public static UserProfile goldUserWith(String aSunrisePassportCode, String emailAddress)
    {
        UserProfile userProfile = new UserProfile();
        userProfile.sunrisePassportCode(aSunrisePassportCode);
        userProfile.type(UserTypeSunrise.GOLD);
        userProfile.email(emailAddress);
        userProfile.mobilePhoneNumber(null);
        return userProfile;
    }
    
    public static UserProfile goldUserWith(String emailAddress)
    {
        return goldUserWith("anySPC", emailAddress);
    }
}
