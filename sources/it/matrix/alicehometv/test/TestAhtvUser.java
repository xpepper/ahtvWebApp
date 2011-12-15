package it.matrix.alicehometv.test;

import static it.matrix.alicehometv.test.AhtvUserCreator.*;
import static org.junit.Assert.*;
import it.matrix.alicehometv.AhtvUser;
import it.matrix.alicehometv.UserPDPBuilder;
import it.matrix.services.userprivacydata.UserPDP;

import org.junit.Test;

public class TestAhtvUser
{
    @Test
    @SuppressWarnings("unused")
    public void testUserIds() throws Exception
    {
        int ahtvId;
        AhtvUser ahtvUser = new AhtvUser(ahtvId = 123, "SPC75636");
        assertEquals(123, ahtvUser.ahtvId());
        assertEquals("SPC75636", ahtvUser.sunrisePassportCode());
    }
    
    @Test
    @SuppressWarnings("unused")
    public void usersWithInvalidAhtvIdShouldBeUnknownUser() throws Exception
    {
        int ahtvId;
        AhtvUser unknown = new AhtvUser(ahtvId = 0, "anySPC");
        assertTrue(unknown.isUnknown());
        assertFalse(unknown.exists());
        
        AhtvUser existing = new AhtvUser(ahtvId = 123, "anySPC");
        assertFalse(existing.isUnknown());
        assertTrue(existing.exists());
    }

    @Test
    public void testAhtvEmailCertification() throws Exception
    {
        AhtvUser ahtvUser = simpleAhtvUser();
        ahtvUser.ahtvEmail("email@address.com");
        assertFalse("by default, the email is not certified", ahtvUser.hasCertifiedAhtvEmail());

        ahtvUser.certifyAhtvEMail(true);
        assertTrue(ahtvUser.hasCertifiedAhtvEmail());

        ahtvUser.certifyAhtvEMail(false);
        assertFalse(ahtvUser.hasCertifiedAhtvEmail());
    }

    @Test
    public void testAhtvMobileCertification() throws Exception
    {
        AhtvUser ahtvUser = simpleAhtvUser();
        ahtvUser.ahtvMobileNumber("+3932911223344");
        assertFalse("by default, the mobile number is not certified", ahtvUser.hasCertifiedAhtvMobile());

        ahtvUser.certifyAhtvMobile(true);
        assertTrue(ahtvUser.hasCertifiedAhtvMobile());

        ahtvUser.certifyAhtvMobile(false);
        assertFalse(ahtvUser.hasCertifiedAhtvMobile());
    }

    @Test
    public void undefinedAhtvMobileIsAlwaysNotCertified() throws Exception
    {
        AhtvUser ahtvUser = simpleAhtvUser();

        ahtvUser.certifyAhtvMobile(true);
        assertFalse(ahtvUser.hasCertifiedAhtvMobile());

        ahtvUser.certifyAhtvMobile(false);
        assertFalse(ahtvUser.hasCertifiedAhtvMobile());
    }

    @Test
    public void undefinedAhtvEmailIsAlwaysNotCertified() throws Exception
    {
        AhtvUser ahtvUser = simpleAhtvUser();

        ahtvUser.certifyAhtvEMail(true);
        assertFalse(ahtvUser.hasCertifiedAhtvEmail());

        ahtvUser.certifyAhtvEMail(false);
        assertFalse(ahtvUser.hasCertifiedAhtvEmail());
    }

    @Test
    public void testHasPendingDataForExistingUser() throws Exception
    {
        AhtvUser ahtvUser = simpleAhtvUser();
        assertFalse(ahtvUser.hasPendingData());

        ahtvUser.ahtvMobileNumber("+3932911223344");
        ahtvUser.ahtvEmail("email@address.com");

        ahtvUser.certifyAhtvEMail(true);
        ahtvUser.certifyAhtvMobile(true);
        assertFalse(ahtvUser.hasPendingData());

        ahtvUser.certifyAhtvMobile(false);
        assertTrue(ahtvUser.hasPendingData());

        ahtvUser.certifyAhtvMobile(true);
        ahtvUser.certifyAhtvEMail(false);
        assertTrue(ahtvUser.hasPendingData());

        ahtvUser.certifyAhtvMobile(false);
        assertTrue(ahtvUser.hasPendingData());
    }

    @Test
    public void unknownUserShouldHaveNoPendingData() throws Exception
    {
        assertFalse(unknownAhtvUser().hasPendingData());
    }

    @Test
    public void unknownUserShouldHaveNoDataCertified() throws Exception
    {
        assertFalse(unknownAhtvUser().hasCertifiedAhtvEmail());
        assertFalse(unknownAhtvUser().hasCertifiedAhtvMobile());
    }
    
    @Test
    public void testAhtvUserFactoryFromUserPDP() throws Exception
    {
        assertTrue(AhtvUser.from(null).isUnknown());
        assertTrue(AhtvUser.from(new UserPDP()).isUnknown());
        
        AhtvUser user = AhtvUser.from(UserPDPBuilder.newUserWith("SPC1234"));
        assertTrue(user.isUnknown());
        
        user = AhtvUser.from(UserPDPBuilder.existingUserWith(7890, "SPC9999"));
        assertFalse(user.isUnknown());
    }    

}
