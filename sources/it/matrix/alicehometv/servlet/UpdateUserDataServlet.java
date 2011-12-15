package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.ServiceResponse;
import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.profile.iptv.IPTVProfile;
import it.matrix.alicehometv.util.AhtvCookie;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;

@SuppressWarnings("serial")
public class UpdateUserDataServlet extends AbstractMyAhtvJsonServlet
{
    @Override
    protected String runOn(XStream converter, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException
    {
        String cli = ahtvCookieFrom(request).get(AhtvCookie.CLI_KEY);
        String purchasePinCode = request.getParameter("controlCode");
        String parentalControlCode = request.getParameter("parentalControlCode");
        String parentalControlLevel = request.getParameter("parentalControlLevel");
        IPTVProfile iptvProfile = new IPTVProfile(cli, purchasePinCode, parentalControlCode, new Integer(parentalControlLevel));

        ActivityLogger.info("Updating IPTV profile for cli:" + cli + " to profile:" + iptvProfile);
        boolean updatedSuccessfully = iptvUserProfileBroker().modifyProfileWith(iptvProfile);

        if (updatedSuccessfully)
            return converter.toXML(ServiceResponse.okResponseWith("IPTV user profile saved successfully"));
        else
            return converter.toXML(ServiceResponse.errorResponseWith("IPTV user profile not saved"));
    }
}
