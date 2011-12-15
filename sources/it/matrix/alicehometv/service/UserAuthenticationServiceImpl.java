package it.matrix.alicehometv.service;

import static org.apache.commons.lang.StringUtils.*;
import it.matrix.alicehometv.UserProfile;
import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.services.user.*;
import it.matrix.services.userprofiledata.UserProfiledataService;
import it.matrix.services.userprofiledata.UserProfiledataServicePortType;

import java.net.MalformedURLException;
import java.net.URL;


public class UserAuthenticationServiceImpl implements UserAuthenticationService
{
    private UserServicePortType itsUserService;
    private UserProfiledataServicePortType itsUserProfileTypeService;

    public UserAuthenticationServiceImpl(URL userServiceLocation, URL userProfileServiceLocation) throws MalformedURLException
    {
        this(new UserService(userServiceLocation).getUserServiceHttpPort(), new UserProfiledataService(userProfileServiceLocation).getUserProfiledataServiceHttpPort());
    }
    
    public UserAuthenticationServiceImpl(UserServicePortType aUserService, UserProfiledataServicePortType aUserProfileTypeService)
    {
        itsUserService = aUserService;
        itsUserProfileTypeService = aUserProfileTypeService;
    }
    
    public boolean isIptvUser(String aSunrisePassportCode)
    {
        boolean isIptvUser = false;
        try
        {
            isIptvUser = itsUserProfileTypeService.isAliceHomeTvUserFromSpc(aSunrisePassportCode);
            ActivityLogger.debug("User Authentication Service find that user with SPC:" + aSunrisePassportCode + " is Iptv User: " + isIptvUser);
        }
        catch (it.matrix.services.userprofiledata.UserNotFoundException_Exception userNotFoundException)
        {
            ActivityLogger.warning(userNotFoundMessage(aSunrisePassportCode, userNotFoundException));
        }
        return isIptvUser;
    }

    public UserProfile userProfileFor(String aSunrisePassportCode)
    {
        UserDetail user = null;
        try
        {
            user = itsUserService.getUserDataFromSpc(aSunrisePassportCode);
        }
        catch (UserNotFoundException_Exception userNotFoundException)
        {
            ActivityLogger.warning(userNotFoundMessage(aSunrisePassportCode, userNotFoundException));
            return null;
        }

        UserPersonalData userPersonalData = user.getUserPersonalData().getValue();
        UserProfileData userProfileData = user.getUserProfileData().getValue();
        
        UserProfile userProfile = new UserProfile();
        userProfile.name(userPersonalData.getName().getValue().getFName().getValue());
        userProfile.surname(userPersonalData.getName().getValue().getLName().getValue());
        userProfile.username(userProfileData.getUsername().getValue()); 
        userProfile.email(userPersonalData.getEmailAddress().getValue());
        userProfile.mobilePhoneNumber(userPersonalData.getMobilePhoneNumber().getValue());
        userProfile.sunrisePassportCode(userProfileData.getSpc().getValue());
        userProfile.cli(userProfileData.getCli().getValue());
        userProfile.type(userProfileData.getSnrsType().getValue());
        
        Address address = userPersonalData.getAddress().getValue();
        Street street = address.getStreet().getValue();
        userProfile.address(format(street));
        userProfile.city(address.getCity().getValue());
        userProfile.zipCode(address.getZipCode().getValue());
        
        ActivityLogger.debug("User Authentication Service find user profile:" + userProfile);
        
        return userProfile;
    }

    private String format(Street street)
    {
        if (isEmpty(street.getAddress().getValue()) && isEmpty(street.getNumber().getValue()))
            return EMPTY;
        else
            return street.getPrefix().getValue() + " " + street.getAddress().getValue() + ", " + street.getNumber().getValue();
    }

    private String userNotFoundMessage(String aSunrisePassportCode, Exception exception)
    {
        return "User with SPC:" + aSunrisePassportCode + " not found - " + exception.getMessage();
    }
}
