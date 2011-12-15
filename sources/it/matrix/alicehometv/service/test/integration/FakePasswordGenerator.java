package it.matrix.alicehometv.service.test.integration;

import it.matrix.alicehometv.PasswordGenerator;

public class FakePasswordGenerator implements PasswordGenerator
{
    private final String itsGeneratedPassword;
 
    public FakePasswordGenerator(String generatedPassword)
    {
        itsGeneratedPassword = generatedPassword;
    }
    
    public String generateNewPassword()
    {
        return itsGeneratedPassword;
    }

}
