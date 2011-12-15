package it.matrix.alicehometv;

import it.matrix.alicehometv.profile.iptv.IPTVProfile;
import it.matrix.alicehometv.profile.iptv.NullIPTVProfile;

@SuppressWarnings("unused")
public class CompositeUserProfile
{
    private final UserProfile userProfile;
    private final AhtvUser ahtvUser;
    private IPTVProfile iptvProfile;
    private ServiceResponse serviceResponse;

    public CompositeUserProfile(UserProfile aUserProfile, AhtvUser anAhtvUser, IPTVProfile anIptvProfile)
    {
        userProfile = aUserProfile;
        ahtvUser = anAhtvUser;

        if (anIptvProfile instanceof NullIPTVProfile)
            serviceResponse = ServiceResponse.iptvErrorResponse();
        else
            iptvProfile = anIptvProfile;
    }
}
