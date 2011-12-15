package it.matrix.alicehometv.service.test;

import static it.matrix.alicehometv.service.test.ResponseCreator.*;
import static it.matrix.alicehometv.test.AhtvUserCreator.*;
import static junit.framework.Assert.*;
import it.matrix.alicehometv.AhtvUser;
import it.matrix.alicehometv.service.AhtvUserServiceImpl;
import it.matrix.alicehometv.test.AhtvUserBuilder;
import it.matrix.services.userprivacydata.UserDP;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestAhtvUserService 
{
    private Mockery context = new JUnit4Mockery();
    private UserDP mockUserDP;
    private AhtvUserServiceImpl itsAhtvUserService;

    @Before
    public void setUp() 
    {
        mockUserDP = context.mock(UserDP.class);
        itsAhtvUserService = new AhtvUserServiceImpl(mockUserDP, null, null);
    }
    
    @Test
    public void testUpdateOriginalContacts() throws Exception
    {
        context.checking(new Expectations()
        {{            
            one(mockUserDP).modifyUserEmail("AHTV", 123, null, "email_sunrise", "newSunriseEmailAddress"); will(returnValue(okEmailResponse()));
            one(mockUserDP).modifyUserPhone("AHTV", 123, null, "mobile_sunrise", "newSunriseMobileNumber"); will(returnValue(okPhoneResponse()));
        }});

        boolean operationSucceed = itsAhtvUserService.updateOriginalContactsFor(ahtvUserWithId(123), "newSunriseEmailAddress", "newSunriseMobileNumber");
        
        assertTrue(operationSucceed);
   }
    
    @Test
    public void testFailingUpdateOriginalContacts() throws Exception
    {
        context.checking(new Expectations()
        {{            
            one(mockUserDP).modifyUserEmail("AHTV", 123, null, "email_sunrise", "newSunriseEmailAddress"); will(returnValue(failingEmailResponse()));
            one(mockUserDP).modifyUserPhone("AHTV", 123, null, "mobile_sunrise", "newSunriseMobileNumber"); will(returnValue(okPhoneResponse()));
        }});

        boolean operationSucceed = itsAhtvUserService.updateOriginalContactsFor(ahtvUserWithId(123), "newSunriseEmailAddress", "newSunriseMobileNumber");
        
        assertFalse(operationSucceed);
   }
    
    @Test
    public void shouldNotUpdateOriginalEmailWhenHasNotBeenChanged() throws Exception
    {
        context.checking(new Expectations()
        {{            
            never(mockUserDP).modifyUserEmail("AHTV", 123, null, "email_sunrise", "sameSunriseEmailAddress"); 
            one(mockUserDP).modifyUserPhone("AHTV", 123, null, "mobile_sunrise", "newSunriseMobileNumber"); will(returnValue(okPhoneResponse()));
        }});

        AhtvUser ahtvUser = new AhtvUserBuilder(123).withOriginalEmail("sameSunriseEmailAddress").withOriginalMobile("oldMobileNumber").build();
        boolean operationSucceed = itsAhtvUserService.updateOriginalContactsFor(ahtvUser, "sameSunriseEmailAddress", "newSunriseMobileNumber");
        
        assertTrue(operationSucceed);
   }
    
    @Test
    public void shouldNotUpdateOriginalPhoneWhenHasNotBeenChanged() throws Exception
    {
        context.checking(new Expectations()
        {{            
            one(mockUserDP).modifyUserEmail("AHTV", 123, null, "email_sunrise", "newSunriseEmailAddress"); will(returnValue(okEmailResponse()));
            never(mockUserDP).modifyUserPhone("AHTV", 123, null, "mobile_sunrise", "sameSunriseMobileNumber"); 
        }});

        AhtvUser ahtvUser = new AhtvUserBuilder(123).withOriginalEmail("oldEmailAddress").withOriginalMobile("sameSunriseMobileNumber").build();
        boolean operationSucceed = itsAhtvUserService.updateOriginalContactsFor(ahtvUser, "newSunriseEmailAddress", "sameSunriseMobileNumber");
        
        assertTrue(operationSucceed);
   }
}
