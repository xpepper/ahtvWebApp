package it.matrix.alicehometv.service;

import it.matrix.alicehometv.UserProfile;


public interface AhtvUserServiceFacade
{
    public int saveNewUserWith(String sunrisePassportCode, String submittedEmailAddress, String submittedMobileNumber);
    public boolean isAValidId(int ahtvId);
    public boolean updateUserWith(Integer ahtvUserId, String newAhtvEmail, String newAhtvMobileNumber);
    public void checkForSunriseProfileDataUpdateFor(UserProfile aUserProfile);
}
