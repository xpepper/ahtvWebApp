package it.matrix.alicehometv.service.test.integration;

import static org.junit.Assert.*;
import it.matrix.alicehometv.UserProfile;
import it.matrix.alicehometv.service.UserAuthenticationService;
import it.matrix.alicehometv.service.UserAuthenticationServiceImpl;
import it.matrix.services.user.UserService;
import it.matrix.services.user.UserServicePortType;
import it.matrix.services.userprofiledata.UserProfiledataService;
import it.matrix.services.userprofiledata.UserProfiledataServicePortType;

import java.net.URL;

import org.junit.Test;

public class TestUserAuthenticationService 
{
    @Test
    public void testUserProfile() throws Exception
    {
        UserServicePortType userService = new UserService(new URL("http://alicehometv2008.devsvil.alice.it/aa/services/user-service?wsdl")).getUserServiceHttpPort();
        UserProfiledataServicePortType userProfileService = new UserProfiledataService(new URL("http://alicehometv2008.devsvil.alice.it/aa/services/user-profiledata-service?wsdl")).getUserProfiledataServiceHttpPort();
        UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl(userService, userProfileService);
        
        UserProfile userProfile = userAuthenticationService.userProfileFor("SRS1767");
        
        assertEquals("SPC1673", userProfile.sunrisePassportCode());
        assertEquals("Mario", userProfile.name());
        assertEquals("Rossi", userProfile.surname());
        assertEquals("Via Pasubio, 21", userProfile.address());
        assertEquals("Milano", userProfile.city());
        assertEquals("20100", userProfile.zipCode());
        assertEquals("028358754", userProfile.cli());
        assertEquals(4, userProfile.type());

        assertEquals("mario.rossi@rossoalice.it", userProfile.email());
        assertEquals("mariorossi72", userProfile.username());
        assertEquals("+393383958971", userProfile.mobilePhoneNumber()); 
    }
}
