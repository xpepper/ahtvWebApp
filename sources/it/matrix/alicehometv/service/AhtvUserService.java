package it.matrix.alicehometv.service;

import it.matrix.alicehometv.AhtvUser;
import it.matrix.alicehometv.UserProfile;

public interface AhtvUserService
{
    static final String AHTV_CHANNEL_ID = "AHTV";
    
    static final String CONTROL_CODE_FOR_MOBILE = "mobile";
    static final String CONTROL_CODE_FOR_EMAIL = "email";

    AhtvUser findUserBySPC(String aSunrisePassportCode);
    int saveNewUser(UserProfile userProfile, String ahtvEmailAddress, String ahtvMobileNumber);
    boolean notifyPendingMobileForUser(int ahtvUserId);
    boolean notifyPendingEmailForUser(int ahtvUserId);
    boolean notifyNewLoginFrom(int ahtvUserId, String userProfileType);
    boolean updateAhtvContactsFor(int ahtvUserId, String newEmailAddress, String newMobileNumber);
    boolean updateOriginalContactsFor(AhtvUser ahtvUser, String newSunriseEmailAddress, String newSunriseMobileNumber);
    OTPVerificationResult verifyPendingOTPFor(int ahtvUserId, String passwordToVerify, String controlCodeType);
}
