package it.matrix.alicehometv.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.UserProfile;

import org.junit.Test;

public class TestUserProfile
{
    @Test
    public void testIsGold() throws Exception
    {
        UserProfile goldUserProfile = UserProfileCreator.goldUserWithUsername("anyUsername");
        
        assertTrue(goldUserProfile.isGold());
        assertFalse(goldUserProfile.isGoldConvergente());
    }
    
    @Test
    public void testIsGoldConvergente() throws Exception
    {
        UserProfile goldConvergenteUserProfile = UserProfileCreator.goldConvergenteUserWith("any@mail.com", "anyNumber");
        
        assertTrue(goldConvergenteUserProfile.isGoldConvergente());
        assertFalse(goldConvergenteUserProfile.isGold());
    }
}

