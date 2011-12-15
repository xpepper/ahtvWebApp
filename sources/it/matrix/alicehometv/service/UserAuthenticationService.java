package it.matrix.alicehometv.service;

import it.matrix.alicehometv.UserProfile;

public interface UserAuthenticationService
{
    public boolean isIptvUser(String aSunrisePassportCode);
    public UserProfile userProfileFor(String aSunrisePassportCode);
}
