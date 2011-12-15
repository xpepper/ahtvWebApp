package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.AhtvUser;
import it.matrix.alicehometv.CompositeUserProfile;
import it.matrix.alicehometv.ServiceResponse;
import it.matrix.alicehometv.UserProfile;
import it.matrix.alicehometv.profile.iptv.IPTVProfile;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;

@SuppressWarnings("serial")
public class UserDataServlet extends AbstractMyAhtvJsonServlet
{
    protected String runOn(XStream converter, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException
    {
        String sunrisePassportCode = sunrisePassportCodeFrom(request);

        UserProfile userProfile = userAuthenticationService().userProfileFor(sunrisePassportCode);
        AhtvUser ahtvUser = ahtvUserService().findUserBySPC(sunrisePassportCode);
        IPTVProfile iptvProfile = iptvUserProfileBroker().finderForCli(userProfile.cli());

        Object jsonResponse = null;
        if (ahtvUser.isUnknown())
            jsonResponse = ServiceResponse.errorResponseWith("unknown ahtv user with SPC:" + sunrisePassportCode);
        else
        {
            converter.alias("compositeUserProfile", CompositeUserProfile.class);
            jsonResponse = new CompositeUserProfile(userProfile, ahtvUser, iptvProfile);
        }

        return converter.toXML(jsonResponse);
    }
}
