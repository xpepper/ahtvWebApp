package it.matrix.alicehometv.service;

import static org.apache.commons.lang.StringUtils.*;
import it.matrix.alicehometv.AhtvUser;
import it.matrix.alicehometv.UserProfile;
import it.matrix.alicehometv.logger.ActivityLogger;

public class AhtvUserServiceFacadeImpl implements AhtvUserServiceFacade
{
    private AhtvUserService itsAhtvUserService;
    private UserAuthenticationService itsUserAuthenticationService;

    public AhtvUserServiceFacadeImpl(AhtvUserService ahtvUserService, UserAuthenticationService userAuthenticationService)
    {
        itsAhtvUserService = ahtvUserService;
        itsUserAuthenticationService = userAuthenticationService;
    }

    public int saveNewUserWith(String aSunrisePassportCode, String submittedAhtvEmailAddress, String submittedAhtvMobileNumber)
    {
        UserProfile userProfile = itsUserAuthenticationService.userProfileFor(aSunrisePassportCode);
        String ahtvEmailAddress = isEmpty(submittedAhtvEmailAddress) ? userProfile.email() : submittedAhtvEmailAddress;
        String ahtvMobileNumber = isEmpty(submittedAhtvMobileNumber) ? userProfile.mobilePhoneNumber() : submittedAhtvMobileNumber;
        
        int ahtvUserId = itsAhtvUserService.saveNewUser(userProfile, ahtvEmailAddress, ahtvMobileNumber);
        
        notifyPendingMobile(submittedAhtvMobileNumber, ahtvUserId);
        notifyPendingEmail(submittedAhtvEmailAddress, ahtvUserId);            
        
        return ahtvUserId;
    }

    public boolean updateUserWith(Integer ahtvUserId, String newAhtvEmail, String newAhtvMobileNumber)
    {
        if (isEmpty(newAhtvEmail) && isEmpty(newAhtvMobileNumber))
            return false;
        else
            return itsAhtvUserService.updateAhtvContactsFor(ahtvUserId.intValue(), newAhtvEmail, newAhtvMobileNumber);
    }
    
    public boolean isAValidId(int ahtvUserId)
    {
        return ahtvUserId != 0;
    }

    private void notifyPendingEmail(String anEmailAddress, int ahtvUserId)
    {
        if (isAValidId(ahtvUserId) && isNotEmpty(anEmailAddress))
        {
            ActivityLogger.info("Notifying pending email:" + anEmailAddress + " for ahtv user id:" + ahtvUserId);
            boolean notifiedSuccessfully = itsAhtvUserService.notifyPendingEmailForUser(ahtvUserId);
            if (!notifiedSuccessfully)
                ActivityLogger.error("Failed to notify pending email: " + anEmailAddress + " for user with ahtv user id:" + ahtvUserId);
        }
    }

    private void notifyPendingMobile(String aMobileNumber, int ahtvUserId)
    {
        if (isAValidId(ahtvUserId) && isNotEmpty(aMobileNumber))
        {
            boolean notifiedSuccessfully = itsAhtvUserService.notifyPendingMobileForUser(ahtvUserId);
            if (!notifiedSuccessfully)
                ActivityLogger.error("Failed to notify pending mobile: " + aMobileNumber + " for user with ahtv user id:" + ahtvUserId);
        }
    }

    public void checkForSunriseProfileDataUpdateFor(UserProfile userProfile)
    {
        AhtvUser ahtvUser = itsAhtvUserService.findUserBySPC(userProfile.sunrisePassportCode());
        if (ahtvUser.exists())
        {
            itsAhtvUserService.notifyNewLoginFrom(ahtvUser.ahtvId(), userProfile.typeAsString());   //TODO eventualmente spostarlo fuori da questo metodo
            itsAhtvUserService.updateOriginalContactsFor(ahtvUser, userProfile.email(), userProfile.mobilePhoneNumber());
        }
    }
}
