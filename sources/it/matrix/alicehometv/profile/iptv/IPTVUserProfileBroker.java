package it.matrix.alicehometv.profile.iptv;

public interface IPTVUserProfileBroker
{
    IPTVProfile finderForCli(String cli);
    boolean modifyProfileWith(IPTVProfile profile);
}