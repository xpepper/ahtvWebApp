package it.matrix.alicehometv.service.test;

import static org.junit.Assert.*;
import static it.matrix.alicehometv.util.test.AssertUtils.*;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import it.matrix.alicehometv.UserProfile;
import it.matrix.alicehometv.service.UserAuthenticationService;
import it.matrix.alicehometv.service.UserAuthenticationServiceImpl;
import it.matrix.services.user.*;
import it.matrix.services.userprofiledata.UserNotFoundException_Exception;
import it.matrix.services.userprofiledata.UserProfiledataServicePortType;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestUserAuthenticationService 
{
    private Mockery context = new JUnit4Mockery();
    
    private UserServicePortType mockUserService;
    private UserProfiledataServicePortType mockUserProfiledataService;

    @Before
    public void setUp() 
    {
        mockUserProfiledataService = context.mock(UserProfiledataServicePortType.class);
        mockUserService = context.mock(UserServicePortType.class);
    }
    
    @Test
    public void testIsIptvUser() throws Exception
    {
        context.checking(new Expectations()
        {{            
            one(mockUserProfiledataService).isAliceHomeTvUserFromSpc("IptvUserSPC"); will(returnValue(true));
            one(mockUserProfiledataService).isAliceHomeTvUserFromSpc("notIptvUserSPC"); will(returnValue(false));
        }});
        
        UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl(null, mockUserProfiledataService);

        assertTrue(userAuthenticationService.isIptvUser("IptvUserSPC"));
        assertFalse(userAuthenticationService.isIptvUser("notIptvUserSPC"));
    }
    
    @Test
    public void testIsIptvUserShouldReturnFalseWhenUserIsNotFound() throws Exception
    {
        context.checking(new Expectations()
        {{            
            one(mockUserProfiledataService).isAliceHomeTvUserFromSpc("notExistentUser"); will(throwException(new UserNotFoundException_Exception("any message", new it.matrix.services.userprofiledata.UserNotFoundException())));
        }});
        
        UserAuthenticationService userAuthenticationService = new UserAuthenticationServiceImpl(null, mockUserProfiledataService);

        assertFalse(userAuthenticationService.isIptvUser("notExistentUser"));
    }

    @Test
    public void testUserProfileShouldReturnNullWhenUserIsNotFound() throws Exception
    {
        context.checking(new Expectations()
        {{            
            one(mockUserService).getUserDataFromSpc("notExistentUser"); will(throwException(new it.matrix.services.user.UserNotFoundException_Exception("any message", new UserNotFoundException())));
        }});
        
        UserProfile userProfile = new UserAuthenticationServiceImpl(mockUserService, null).userProfileFor("notExistentUser");
        
        assertNull(userProfile);
    }
    
    @Test
    public void addressShouldBeEmptyWhenAddressFromUserServiceIsUndefined() throws Exception
    {
        context.checking(new Expectations()
        {{            
            one(mockUserService).getUserDataFromSpc("SPC1234"); will(returnValue(emptyUserDetailWith("SPC1234")));
        }});
        
        UserProfile userProfile = new UserAuthenticationServiceImpl(mockUserService, null).userProfileFor("SPC1234");
        
        assertEmpty(userProfile.address());
    }

    @Test
    public void addressShouldBeFormattedCorrectly() throws Exception
    {
        context.checking(new Expectations()
        {{            
            one(mockUserService).getUserDataFromSpc("SPC1234"); will(returnValue(userDetailWithAddress("Corso", "Garibaldi", "23", "SPC1234")));
        }});
        
        UserProfile userProfile = new UserAuthenticationServiceImpl(mockUserService, null).userProfileFor("SPC1234");
        
        assertEquals("Corso Garibaldi, 23", userProfile.address());
    }

    private UserDetail userDetailWithAddress(String anAddressPrefix, String anAddress, String anAddressNumber, String aSunrisePassportCode)
    {
        UserDetail user = new UserDetail();

        QName anyQName = new QName("");
        UserPersonalData userPersonalData = new UserPersonalData();
        user.setUserPersonalData(new JAXBElement<UserPersonalData>(anyQName, UserPersonalData.class, userPersonalData));

        UserProfileData userProfileData = new UserProfileData();
        user.setUserProfileData(new JAXBElement<UserProfileData>(anyQName, UserProfileData.class, userProfileData));

        Street street = new Street();
        street.setPrefix(new JAXBElement<String>(anyQName, String.class, anAddressPrefix));
        street.setAddress(new JAXBElement<String>(anyQName, String.class, anAddress));
        street.setNumber(new JAXBElement<String>(anyQName, String.class, anAddressNumber));
        
        Address address = new Address();
        address.setStreet(new JAXBElement<Street>(anyQName, Street.class, street));
        address.setCity(new JAXBElement<String>(anyQName, String.class, ""));
        address.setZipCode(new JAXBElement<String>(anyQName, String.class, ""));
        userPersonalData.setAddress(new JAXBElement<Address>(anyQName, Address.class, address));
        
        userPersonalData.setEmailAddress(new JAXBElement<String>(anyQName, String.class, ""));
        userPersonalData.setMobilePhoneNumber(new JAXBElement<String>(anyQName, String.class, ""));
        
        Name name = new Name();
        name.setFName(new JAXBElement<String>(anyQName, String.class, ""));
        name.setLName(new JAXBElement<String>(anyQName, String.class, ""));
        userPersonalData.setName(new JAXBElement<Name>(anyQName, Name.class, name ));
        
        userProfileData.setUsername(new JAXBElement<String>(anyQName, String.class, ""));
        userProfileData.setSpc(new JAXBElement<String>(anyQName, String.class, aSunrisePassportCode));
        userProfileData.setCli(new JAXBElement<String>(anyQName, String.class, ""));
        userProfileData.setSnrsType(new JAXBElement<Integer>(anyQName, Integer.class, new Integer(1)));
        
        return user;
    }
    
    private UserDetail emptyUserDetailWith(String aSunrisePassportCode)
    {
        return  userDetailWithAddress("", "", "", aSunrisePassportCode);
    }
}
