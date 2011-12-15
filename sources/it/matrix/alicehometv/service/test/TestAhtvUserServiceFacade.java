package it.matrix.alicehometv.service.test;

import static it.matrix.alicehometv.test.AhtvUserCreator.*;
import static it.matrix.alicehometv.test.UserProfileCreator.*;
import static junit.framework.Assert.*;
import it.matrix.alicehometv.AhtvUser;
import it.matrix.alicehometv.UserProfile;
import it.matrix.alicehometv.service.*;
import it.matrix.alicehometv.test.AhtvUserBuilder;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestAhtvUserServiceFacade 
{
    private Mockery context = new JUnit4Mockery();
    
    private AhtvUserService mockAhtvUserService;
    private UserAuthenticationService mockUserAuthenticationService;
    private AhtvUserServiceFacadeImpl itsUserServiceFacade;
    
    @SuppressWarnings("unused")
    private String submittedAhtvEmail;
    @SuppressWarnings("unused")
    private String submittedAhtvMobile;

    @Before
    public void setUp() 
    {
        mockAhtvUserService = context.mock(AhtvUserService.class);
        mockUserAuthenticationService = context.mock(UserAuthenticationService.class);
        
        itsUserServiceFacade = new AhtvUserServiceFacadeImpl(mockAhtvUserService, mockUserAuthenticationService);
    }
    
    @Test
    @SuppressWarnings("unused")
    public void shouldSaveNewGoldUserWithNoMailAndNoMobileNumberSpecified() throws Exception
    {
        final UserProfile goldUser = goldUserWith("mario_rossi@alice.it");
        context.checking(new Expectations()
        {{            
            one(mockUserAuthenticationService).userProfileFor("SPC1234"); will(returnValue(goldUser));
            one(mockAhtvUserService).saveNewUser(goldUser, "mario_rossi@alice.it", null); will(returnValue(123));
            never(mockAhtvUserService).notifyPendingMobileForUser(123);
            never(mockAhtvUserService).notifyPendingEmailForUser(123);
        }});

        itsUserServiceFacade.saveNewUserWith("SPC1234", submittedAhtvEmail = null, submittedAhtvMobile = null);
    }

    @Test
    @SuppressWarnings("unused")
    public void shouldSaveNewGoldUserWithMobileNumberButWithoutMailSpecified() throws Exception
    {
        final UserProfile goldUser = goldUserWith("mario_rossi@alice.it");
        context.checking(new Expectations()
        {{            
            one(mockUserAuthenticationService).userProfileFor("SPC1234"); will(returnValue(goldUserWith("mario_rossi@alice.it")));
            one(mockAhtvUserService).saveNewUser(goldUser, "mario_rossi@alice.it", "+393498576346"); will(returnValue(123));
            one(mockAhtvUserService).notifyPendingMobileForUser(123); will(returnValue(true));
        }});

        itsUserServiceFacade.saveNewUserWith("SPC1234", submittedAhtvEmail = null, submittedAhtvMobile = "+393498576346");
    }
    
    @Test
    public void shouldSaveNewGoldUserWithEmailButWithoutMobileNumberSpecified() throws Exception
    {
        final UserProfile goldUser = goldUserWith("mario_rossi@alice.it");
        context.checking(new Expectations()
        {{            
            one(mockUserAuthenticationService).userProfileFor("SPC1234"); will(returnValue(goldUserWith("mario_rossi@alice.it")));
            one(mockAhtvUserService).saveNewUser(goldUser, "m.rossi1972@gmail.com", null);  will(returnValue(123));
            one(mockAhtvUserService).notifyPendingEmailForUser(123); will(returnValue(true));
        }});

        itsUserServiceFacade.saveNewUserWith("SPC1234", submittedAhtvEmail = "m.rossi1972@gmail.com", submittedAhtvMobile = null);
    }

    @Test
    public void shouldSaveNewGoldUserWithBothEmailAndMobileNumberSpecified() throws Exception
    {
        final UserProfile goldUser = goldUserWith("mario_rossi@alice.it");
        context.checking(new Expectations()
        {{            
            one(mockUserAuthenticationService).userProfileFor("SPC1234"); will(returnValue(goldUserWith("mario_rossi@alice.it")));
            one(mockAhtvUserService).saveNewUser(goldUser, "m.rossi1972@gmail.com", "+393498576346"); will(returnValue(123));
            one(mockAhtvUserService).notifyPendingMobileForUser(123); will(returnValue(true));
            one(mockAhtvUserService).notifyPendingEmailForUser(123); will(returnValue(true));
        }});

        itsUserServiceFacade.saveNewUserWith("SPC1234", submittedAhtvEmail = "m.rossi1972@gmail.com", submittedAhtvMobile = "+393498576346");
    }

    
    @Test
    public void shouldSaveNewGoldConvergenteUserWithNoMailAndNoMobileNumberSpecified() throws Exception
    {
        final UserProfile goldConvergenteUser = goldConvergenteUserWith("g.verdi@alice.it", "+393381122333");
        context.checking(new Expectations()
        {{            
            one(mockUserAuthenticationService).userProfileFor("SPC6789"); will(returnValue(goldConvergenteUser));
            one(mockAhtvUserService).saveNewUser(goldConvergenteUser, "g.verdi@alice.it", "+393381122333"); will(returnValue(123));
            never(mockAhtvUserService).notifyPendingEmailForUser(123);
            never(mockAhtvUserService).notifyPendingMobileForUser(123);
        }});

        itsUserServiceFacade.saveNewUserWith("SPC6789", submittedAhtvEmail = null, submittedAhtvMobile = null);
    }

    @Test
    public void shouldSaveNewGoldConvergenteUserWithMobileNumberButWithoutMailSpecified() throws Exception
    {
        final UserProfile goldConvergenteUser = goldConvergenteUserWith("SPC6789", "g.verdi@alice.it", "+393381122333");
        context.checking(new Expectations()
        {{            
            one(mockUserAuthenticationService).userProfileFor("SPC6789"); will(returnValue(goldConvergenteUser));
            one(mockAhtvUserService).saveNewUser(goldConvergenteUser, "g.verdi@alice.it", "+39329999999"); will(returnValue(123));
            one(mockAhtvUserService).notifyPendingMobileForUser(123); will(returnValue(true));
        }});

        itsUserServiceFacade.saveNewUserWith("SPC6789", submittedAhtvEmail = null, submittedAhtvMobile = "+39329999999");
    }
    
    @Test
    public void shouldSaveNewGoldConvergenteUserWithEmailButWithoutMobileNumberSpecified() throws Exception
    {
        final UserProfile goldConvergenteUser = goldConvergenteUserWith("g.verdi@alice.it", "+393381122333");
        context.checking(new Expectations()
        {{            
            one(mockUserAuthenticationService).userProfileFor("SPC6789"); will(returnValue(goldConvergenteUser));
            one(mockAhtvUserService).saveNewUser(goldConvergenteUser, "gverdi99@gmail.com", "+393381122333");  will(returnValue(123));
            one(mockAhtvUserService).notifyPendingEmailForUser(123); will(returnValue(true));
        }});

        itsUserServiceFacade.saveNewUserWith("SPC6789", submittedAhtvEmail = "gverdi99@gmail.com", submittedAhtvMobile = null);
    }

    @Test
    public void shouldSaveNewGoldConvergenteUserWithBothEmailAndMobileNumberSpecified() throws Exception
    {
        final UserProfile goldConvergenteUser = goldConvergenteUserWith("g.verdi@alice.it", "+393381122333");
        context.checking(new Expectations()
        {{            
            one(mockUserAuthenticationService).userProfileFor("SPC6789"); will(returnValue(goldConvergenteUser));
            one(mockAhtvUserService).saveNewUser(goldConvergenteUser, "gverdi99@gmail.com", "+39329999999");  will(returnValue(123));
            one(mockAhtvUserService).notifyPendingMobileForUser(123); will(returnValue(true));
            one(mockAhtvUserService).notifyPendingEmailForUser(123); will(returnValue(true));
        }});

        itsUserServiceFacade.saveNewUserWith("SPC6789", submittedAhtvEmail = "gverdi99@gmail.com", submittedAhtvMobile = "+39329999999");
    }
    
    @Test
    public void shouldReturnAnInvalidIdWhenNewUserSavingFails() throws Exception
    {
        final UserProfile goldUser = goldUserWith("SPC6789", "any@alice.it");
        context.checking(new Expectations()
        {{            
            one(mockUserAuthenticationService).userProfileFor("SPC6789"); will(returnValue(goldUser));
            one(mockAhtvUserService).saveNewUser(goldUser, "any@alice.it", null); will(returnValue(0));
        }});

        int returnedId = itsUserServiceFacade.saveNewUserWith("SPC6789", submittedAhtvEmail = "any@alice.it", submittedAhtvMobile = null);
        assertEquals(0, returnedId);
    }
    
    @Test
    public void shouldReturnTheGeneratedIdWhenNewUserSavingSucceeds() throws Exception
    {
        final UserProfile goldUser = goldUserWith("SPC6789", "any@alice.it");
        context.checking(new Expectations()
        {{            
            one(mockUserAuthenticationService).userProfileFor("SPC6789"); will(returnValue(goldUser));
            one(mockAhtvUserService).saveNewUser(goldUser, "any@alice.it", null); will(returnValue(123));
        }});

        int returnedId = itsUserServiceFacade.saveNewUserWith("SPC6789", submittedAhtvEmail = null, submittedAhtvMobile = null);
        assertEquals(123, returnedId);
    }

    @Test
    public void updateUserShouldSkipUpdatingWhenParametersAreEmpty() throws Exception
    {
        context.checking(new Expectations()
        {{            
            never(mockAhtvUserService);
        }});

        boolean updatedSuccessfully = itsUserServiceFacade.updateUserWith(new Integer(123), null, null);
        assertFalse(updatedSuccessfully);
    }
    
    @Test
    public void updateUserShouldUpdateUserEmail() throws Exception
    {
        context.checking(new Expectations()
        {{            
            one(mockAhtvUserService).updateAhtvContactsFor(123, "a.new.email@address.com", null); will(returnValue(true));
        }});

        boolean updatedSuccessfully = itsUserServiceFacade.updateUserWith(new Integer(123), "a.new.email@address.com", null);
        assertTrue(updatedSuccessfully);
    }
    
    @Test
    public void updateUserShouldUpdateUserMobileNumber() throws Exception
    {
        context.checking(new Expectations()
        {{            
            one(mockAhtvUserService).updateAhtvContactsFor(123, null, "+391122334455"); will(returnValue(true));
        }});

        boolean updatedSuccessfully = itsUserServiceFacade.updateUserWith(new Integer(123), null, "+391122334455");
        assertTrue(updatedSuccessfully);
    }
    
    @Test
    public void updateUserShouldReportWhenUserUpdatingFails() throws Exception
    {
        context.checking(new Expectations()
        {{            
            one(mockAhtvUserService).updateAhtvContactsFor(123, null, "+391122334455"); will(returnValue(false));
        }});

        boolean updatedSuccessfully = itsUserServiceFacade.updateUserWith(new Integer(123), null, "+391122334455");
        assertFalse(updatedSuccessfully);
    }    
    
    @Test
    public void shouldCheckForSunriseProfileDataUpdate() throws Exception
    {
        final UserProfile goldConvergenteUser = goldConvergenteUserWith("aSPC", "new.mail@alice.it", "newMobileNumber");
        final AhtvUser existingAhtvUser = new AhtvUserBuilder(123, "aSPC").withOriginalEmail("previous.mail@alice.it").withOriginalMobile("previousMobile").build();
        
        context.checking(new Expectations()
        {{            
            one(mockAhtvUserService).findUserBySPC("aSPC"); will(returnValue(existingAhtvUser));
            one(mockAhtvUserService).notifyNewLoginFrom(123, goldConvergenteUser.typeAsString()); 
            one(mockAhtvUserService).updateOriginalContactsFor(existingAhtvUser, "new.mail@alice.it", "newMobileNumber"); will(returnValue(true));
        }});

        itsUserServiceFacade.checkForSunriseProfileDataUpdateFor(goldConvergenteUser);
    }    
    
    @Test
    public void shouldNotCheckForSunriseProfileDataUpdateWhenUserDoesNotExist() throws Exception
    {
        context.checking(new Expectations()
        {{            
            one(mockAhtvUserService).findUserBySPC("aNotExistingSPC"); will(returnValue(unknownAhtvUser()));
            never(mockAhtvUserService).notifyNewLoginFrom(123, "anyType");will(returnValue(true));
            never(mockAhtvUserService).updateOriginalContactsFor(unknownAhtvUser(), "anyMail", "anyMobileNumber");
        }});

        itsUserServiceFacade.checkForSunriseProfileDataUpdateFor(goldConvergenteUserWith("aNotExistingSPC", "any@alice.it", "3383939399"));
    }  
}
