package it.matrix.alicehometv.service.test.integration;

import static org.junit.Assert.*;
import it.matrix.alicehometv.*;
import it.matrix.alicehometv.db.DbConnectionProvider;
import it.matrix.alicehometv.db.util.AhtvDirectJDBCConnectionProviderFactory;
import it.matrix.alicehometv.service.AhtvUserServiceImpl;
import it.matrix.alicehometv.service.OTPVerificationResult;
import it.matrix.alicehometv.test.UserProfileCreator;
import it.matrix.services.userprivacydata.UserDP;
import it.matrix.services.userprivacydata.UserDPService;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

public class TestAhtvUserService
{
    private String itsSunrisePassportCode;
    private UserDP itsUserPrivacyService;
    private AhtvUserServiceImpl itsAhtvUserService;

    @Before
    public void setUp() throws MalformedURLException
    {
        itsSunrisePassportCode = createNewSunrisePassportCode();
        itsUserPrivacyService = new UserDPService(new URL("http://dp.alice.it:4040/pdp-services/UserDPService?wsdl")).getUserDPPort();
        itsAhtvUserService = new AhtvUserServiceImpl(itsUserPrivacyService, AhtvDirectJDBCConnectionProviderFactory.createForDev(), new PasswordGeneratorImpl());
    }

    @Test
    public void testSaveAndFindUser() throws Exception
    {
        AhtvUser unknownUser = itsAhtvUserService.findUserBySPC("unknownSPC");
        assertTrue(unknownUser.isUnknown());

        UserProfile userProfile = UserProfileCreator.goldConvergenteUserWith(itsSunrisePassportCode, "original@Email.it", "33812345678");
        int ahtvUserId = itsAhtvUserService.saveNewUser(userProfile, "ahtv@Email.it", "32099991111");

        AhtvUser foundUser = itsAhtvUserService.findUserBySPC(itsSunrisePassportCode);

        AhtvUser expectedUser = new AhtvUser(ahtvUserId, itsSunrisePassportCode);
        expectedUser.originalMobileNumber("33812345678");
        expectedUser.ahtvMobileNumber("32099991111");
        expectedUser.certifyAhtvMobile(true);
        expectedUser.originalEmail("original@Email.it");
        expectedUser.ahtvEmail("ahtv@Email.it");
        expectedUser.certifyAhtvEMail(true);

        assertEquals(expectedUser, foundUser);
    }

    @Test
    public void undefinedMailAndMobileShouldBeNotCertified() throws Exception
    {
        saveNewUserWithNoMailAndMobileWith(itsSunrisePassportCode);

        AhtvUser userWithNoMailAndMobile = itsAhtvUserService.findUserBySPC(itsSunrisePassportCode);

        assertFalse(userWithNoMailAndMobile.hasCertifiedAhtvEmail());
        assertFalse(userWithNoMailAndMobile.hasCertifiedAhtvMobile());
        assertFalse(userWithNoMailAndMobile.hasPendingData());
    }
    
    @Test
    public void shouldSaveNullMobileNumbersWhenSavingNewUserWithNullMobileNumbers() throws Exception
    {
        UserProfile userProfile = UserProfileCreator.goldUserWith(itsSunrisePassportCode, "any@alice.it");
        itsAhtvUserService.saveNewUser(userProfile, "any@email.com", null);

        AhtvUser foundUser = itsAhtvUserService.findUserBySPC(itsSunrisePassportCode);

        assertNull(foundUser.originalMobileNumber());
        assertNull(foundUser.ahtvMobileNumber());
    }

    @Test
    public void notifyPendingDataShouldSaveOTPCorrectly() throws Exception
    {
        int ahtvUserId = saveNewUserWith(itsSunrisePassportCode);

        PasswordGenerator passwordGenerator = new FakePasswordGenerator("an OTP for test");
        itsAhtvUserService = new AhtvUserServiceImpl(itsUserPrivacyService, AhtvDirectJDBCConnectionProviderFactory.createForDev(), passwordGenerator);

        boolean notificationSucceed = itsAhtvUserService.notifyPendingEmailForUser(ahtvUserId);
        assertTrue(notificationSucceed);
        assertEquals("an OTP for test", findStoredOTPFor(ahtvUserId, "EMAIL"));
        
        notificationSucceed = itsAhtvUserService.notifyPendingMobileForUser(ahtvUserId);
        assertTrue(notificationSucceed);
        assertEquals("an OTP for test", findStoredOTPFor(ahtvUserId, "SMS"));
    }

    @Test
    public void testHasPendingDataForNotExistingUser() throws Exception
    {
        AhtvUser unknownUser = itsAhtvUserService.findUserBySPC("unknownSPC");
        assertTrue(unknownUser.isUnknown());
        assertFalse(unknownUser.hasPendingData());
    }

    @Test
    public void testUpdateAhtvEmail() throws Exception
    {
        UserProfile userProfile = UserProfileCreator.goldConvergenteUserWith(itsSunrisePassportCode, "original@email.it", "33812345678");
        int ahtvUserId = itsAhtvUserService.saveNewUser(userProfile, "old.ahtv@email.it", "32099991111");

        AhtvUser retrievedUser = itsAhtvUserService.findUserBySPC(itsSunrisePassportCode);
        assertFalse("initially, user should NOT have any pending data", retrievedUser.hasPendingData());
        
        itsAhtvUserService.updateAhtvContactsFor(ahtvUserId, "new.ahtv@email.it", null);
        
        retrievedUser = itsAhtvUserService.findUserBySPC(itsSunrisePassportCode);

        assertEquals("should have updated the ahtv email", "new.ahtv@email.it", retrievedUser.ahtvEmail());
        assertTrue("user should now have pending data", retrievedUser.hasPendingData());
        assertFalse("user should now have uncertified ahtv email", retrievedUser.hasCertifiedAhtvEmail());
        
        assertEquals("should have NOT updated the ahtv mobile", "32099991111", retrievedUser.ahtvMobileNumber());
        assertTrue("user should still have certified ahtv mobile", retrievedUser.hasCertifiedAhtvMobile());
        
        assertEquals("Original email should NOT BE updated", "original@email.it", retrievedUser.originalEmail());
        assertEquals("Original mobile should NOT BE updated", "33812345678", retrievedUser.originalMobileNumber());
    }
    
    @Test
    public void testUpdateAhtvMobileNumber() throws Exception
    {
        UserProfile userProfile = UserProfileCreator.goldConvergenteUserWith(itsSunrisePassportCode, "original@email.it", "33812345678");
        int ahtvUserId = itsAhtvUserService.saveNewUser(userProfile, "old.ahtv@email.it", "32099991111");

        AhtvUser retrievedUser = itsAhtvUserService.findUserBySPC(itsSunrisePassportCode);
        assertFalse("initially, user should NOT have any pending data", retrievedUser.hasPendingData());
        
        itsAhtvUserService.updateAhtvContactsFor(ahtvUserId, null, "+393307777777");
        
        retrievedUser = itsAhtvUserService.findUserBySPC(itsSunrisePassportCode);

        assertEquals("should have updated the ahtv mobile", "+393307777777", retrievedUser.ahtvMobileNumber());
        assertTrue("user should now have pending data", retrievedUser.hasPendingData());
        assertFalse("user should now have uncertified ahtv mobile", retrievedUser.hasCertifiedAhtvMobile());

        assertEquals("should still have the old ahtv email", "old.ahtv@email.it", retrievedUser.ahtvEmail());
        assertTrue("user should still have certified ahtv email", retrievedUser.hasCertifiedAhtvEmail());
                
        assertEquals("Original email should NOT BE updated", "original@email.it", retrievedUser.originalEmail());
        assertEquals("Original mobile should NOT BE updated", "33812345678", retrievedUser.originalMobileNumber());
    }

    @Test
    public void testUpdateOriginalContactData() throws Exception
    {
        UserProfile userProfile = UserProfileCreator.goldConvergenteUserWith(itsSunrisePassportCode, "original@email.it", "33812345678");
        itsAhtvUserService.saveNewUser(userProfile, "my.personal@email.it", "my.personal.mobile");
        AhtvUser savedUser = itsAhtvUserService.findUserBySPC(itsSunrisePassportCode);

        itsAhtvUserService.updateOriginalContactsFor(savedUser, "new.sunrise@email.it", "new.sunrise.mobile");

        AhtvUser retrievedUser = itsAhtvUserService.findUserBySPC(itsSunrisePassportCode);
        assertEquals("should have updated the original email", "new.sunrise@email.it", retrievedUser.originalEmail());
        assertEquals("should have updated the original mobile", "new.sunrise.mobile", retrievedUser.originalMobileNumber());

        assertFalse("user should NOT have pending data", retrievedUser.hasPendingData());
        
        assertEquals("Ahtv email should NOT BE updated", "my.personal@email.it", retrievedUser.ahtvEmail());
        assertEquals("Ahtv mobile should NOT BE updated", "my.personal.mobile", retrievedUser.ahtvMobileNumber());
    }
    
    @Test
    public void shouldUpdateOriginalContactDataEvenWithNull() throws Exception
    {
        UserProfile userProfile = UserProfileCreator.goldConvergenteUserWith(itsSunrisePassportCode, "original@Email.it", "33812345678");
        itsAhtvUserService.saveNewUser(userProfile, "my.personal@email.it", "my.personal.mobile");
        AhtvUser savedUser = itsAhtvUserService.findUserBySPC(itsSunrisePassportCode);
        
        itsAhtvUserService.updateOriginalContactsFor(savedUser, null, null);

        AhtvUser retrievedUser = itsAhtvUserService.findUserBySPC(itsSunrisePassportCode);
        
        assertNull("should have updated to a null value the original email", retrievedUser.originalEmail());
        assertNull("should have updated to a null value the original mobile", retrievedUser.originalMobileNumber());
    }

    
    @Test
    public void testVerifyPendingOTPShouldFailForUnknownAhtvUser() throws Exception
    {
        OTPVerificationResult result = itsAhtvUserService.verifyPendingOTPFor(999, "passwordToVerify", "email");
        assertFalse("verification should fail when user in not recognized", result.isOk());
        assertFalse("verification should fail when user in not recognized", result.isControlCodeMatching());
        assertFalse("verification should fail when user in not recognized", result.isControlCodeVerified());
    }
    
    @Test
    public void testVerifyPendingOTPShouldFailWhenThereIsNoOTPStored() throws Exception
    {
        int ahtvUserId = saveNewUserWithAhtvContacts(itsSunrisePassportCode, "m.rossi@yahoo.it", "34834930434");
        AhtvUser savedUser = itsAhtvUserService.findUserBySPC(itsSunrisePassportCode);
        assertFalse(savedUser.hasPendingData());
        
        checkNotExistingOTPVerificationProcessFor(ahtvUserId, "email");        
        checkNotExistingOTPVerificationProcessFor(ahtvUserId, "mobile");   
    }

    @Test
    public void testVerifyPendingOTPShouldFailWhenOTPIsWrong() throws Exception
    {
        int ahtvUserId = saveNewUserWithAhtvContacts(itsSunrisePassportCode, "m.rossi@yahoo.it", "34834930434");
        itsAhtvUserService.notifyPendingEmailForUser(ahtvUserId);
        
        OTPVerificationResult result = itsAhtvUserService.verifyPendingOTPFor(ahtvUserId, "wrongPassword", "email");
        
        assertFalse("password should not match", result.isControlCodeMatching());
        assertFalse("OTP should not be verified", result.isControlCodeVerified());
    }
    
    @Test
    public void testVerifyPendingOTP() throws Exception
    {
        int ahtvUserId = saveNewUserWithAhtvContacts(itsSunrisePassportCode, "m.rossi@yahoo.it", "34834930434");
        itsAhtvUserService.notifyPendingEmailForUser(ahtvUserId);
        
        checkVerificationFor(ahtvUserId, "EMAIL", "email");
        
        itsAhtvUserService.notifyPendingMobileForUser(ahtvUserId);

        checkVerificationFor(ahtvUserId, "SMS", "mobile");
    }

    
    @Test
    public void testNotifyNewLogin() throws Exception
    {
        int ahtvUserId = saveNewUserWith(itsSunrisePassportCode);
        boolean operationSucceed = itsAhtvUserService.notifyNewLoginFrom(ahtvUserId, "anyUserType");
        
        assertTrue(operationSucceed);        
    }
    
    
    private void checkVerificationFor(int ahtvUserId, String otpType, String controlCodeType) throws SQLException
    {
        String storedOTP = findStoredOTPFor(ahtvUserId, otpType);

        OTPVerificationResult result = itsAhtvUserService.verifyPendingOTPFor(ahtvUserId, storedOTP, controlCodeType);
        assertFalse("verification process should fail since message with OTP has not been generated yet", result.isOk());
        assertTrue("password should match", result.isControlCodeMatching());
        assertFalse("OTP should not be verified since message with OTP has not been generated yet", result.isControlCodeVerified());
    }

    private void checkNotExistingOTPVerificationProcessFor(int ahtvUserId, String controlCodeType)
    {
        OTPVerificationResult result = itsAhtvUserService.verifyPendingOTPFor(ahtvUserId, "notExistingPasswordToVerify", controlCodeType);
        assertFalse("verification process should fail for not existing password", result.isOk());
        assertFalse("verification check should fail for not existing password", result.isControlCodeMatching());
        assertFalse("verification should fail when for not existing password", result.isControlCodeVerified());
    }    
    
    private String createNewSunrisePassportCode()
    {
        return RandomStringUtils.randomAlphabetic(10);
    }

    private int saveNewUserWithAhtvContacts(String spc, String ahtvEmail, String ahtvMobile)
    {
        UserProfile userProfile = UserProfileCreator.goldConvergenteUserWith(spc, "original@Email.it", "33812345678");
        return itsAhtvUserService.saveNewUser(userProfile, ahtvEmail, ahtvMobile);
    }

    private int saveNewUserWithNoMailAndMobileWith(String sunrisePassportCode)
    {
        UserProfile userProfile = UserProfileCreator.goldConvergenteUserWith(sunrisePassportCode, "original@Email.it", "33812345678");
        return itsAhtvUserService.saveNewUser(userProfile, null, null);
    }
    
    private int saveNewUserWith(String aSunrisePassportCode)
    {
        UserProfile userProfile = UserProfileCreator.goldConvergenteUserWith(aSunrisePassportCode, "original@Email.it", "33812345678");
        return itsAhtvUserService.saveNewUser(userProfile, "ahtv@Email.it", "32099991111");
    }

    private String findStoredOTPFor(int ahtvUserId, String otpType) throws SQLException
    {
        DbConnectionProvider connectionProvider = AhtvDirectJDBCConnectionProviderFactory.createForDev();
        
        String storedOTP = null;
        Connection connection = null;
        try
        {
            connection = connectionProvider.connection();
            CallableStatement statement = connection.prepareCall("call pkg_test.getotputente (?,?,?,?,?)");

            statement.setInt(1, ahtvUserId);
            statement.setString(2, otpType);
            statement.registerOutParameter(3, Types.VARCHAR);
            statement.registerOutParameter(4, Types.VARCHAR);
            statement.registerOutParameter(5, Types.NUMERIC);
            statement.execute();

            storedOTP = statement.getString(3);
            int resultCode = statement.getInt(5);
            assertEquals("Error retrieving the OTP - wrong result code", 0, resultCode);
        }
        finally
        {
            connectionProvider.close(connection);
        }
        return storedOTP;
    }
}
