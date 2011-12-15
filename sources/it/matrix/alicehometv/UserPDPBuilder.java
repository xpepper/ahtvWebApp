package it.matrix.alicehometv;

import it.matrix.services.userprivacydata.UserID;
import it.matrix.services.userprivacydata.UserPDP;

public class UserPDPBuilder
{
    public static UserPDP newUserWith(String sunrisePassportCode)
    {
        UserPDP newUser = new UserPDP();
        UserID userID = new UserID();
        userID.setExtUserId(sunrisePassportCode);
        newUser.setUserId(userID);

        return newUser;
    }

    public static UserPDP existingUserWith(int ahtvUserId, String sunrisePassportCode)
    {
        UserPDP existingUser = UserPDPBuilder.newUserWith(sunrisePassportCode); 
        existingUser.getUserId().setIntUserId(ahtvUserId);

        return existingUser;
    }
}
