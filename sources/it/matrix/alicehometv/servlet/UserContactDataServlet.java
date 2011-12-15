package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.AhtvUser;
import it.matrix.alicehometv.ServiceResponse;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;

@SuppressWarnings("serial")
public class UserContactDataServlet extends AbstractMyAhtvJsonServlet
{
    protected String runOn(XStream converter, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException
    {
        String sunrisePassportCode = sunrisePassportCodeFrom(request);

        AhtvUser ahtvUser = ahtvUserService().findUserBySPC(sunrisePassportCode);

        Object jsonResponse = null;
        if (ahtvUser.isUnknown())
            jsonResponse = ServiceResponse.errorResponseWith("unknown ahtv user with SPC:" + sunrisePassportCode);
        else
        {
            converter.alias("ahtvUser", AhtvUser.class);
            jsonResponse = ahtvUser;
        }

        return converter.toXML(jsonResponse);
    }

}
