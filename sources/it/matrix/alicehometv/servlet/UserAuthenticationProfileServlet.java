package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.*;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;

@SuppressWarnings("serial")
public class UserAuthenticationProfileServlet extends AbstractMyAhtvJsonServlet
{
    protected String runOn(XStream converter, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException
    {
        String sunrisePassportCode = sunrisePassportCodeFrom(request);

        UserProfile userProfile = userAuthenticationService().userProfileFor(sunrisePassportCode);

        converter.alias("userProfile", UserProfile.class);
        return converter.toXML(userProfile);
    }
}
