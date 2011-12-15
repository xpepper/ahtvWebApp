package it.matrix.alicehometv;

import org.apache.commons.lang.RandomStringUtils;

public class PasswordGeneratorImpl implements PasswordGenerator
{
    public String generateNewPassword()
    {
        return RandomStringUtils.randomAlphabetic(8);
    }
}
