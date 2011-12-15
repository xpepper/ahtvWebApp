package it.matrix.alicehometv.servlet.test;

import it.matrix.alicehometv.profile.iptv.IPTVProfile;

public class IPTVProfileCreator
{
    public static IPTVProfile iptvProfileWith(String purchasePin, String pcPin, Integer pcLevelCode)
    {
        IPTVProfile iptvProfile = new IPTVProfile();
        iptvProfile.setPcPin(pcPin);
        iptvProfile.setPcLevel(pcLevelCode);
        iptvProfile.setPurchasePin(purchasePin);
        return iptvProfile;
    }

}
