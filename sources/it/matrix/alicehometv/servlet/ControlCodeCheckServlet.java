package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.*;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;


@SuppressWarnings("serial")
public class ControlCodeCheckServlet extends AbstractMyAhtvJsonServlet
{
    public static final String CONTROL_CODE_PARAMETER_NAME = "controlCode";
    private static final String CONTROL_CODE_TYPE_PARAMETER_NAME = "controlCodeType";

    protected String runOn(XStream converter, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException
    {
        int ahtvUserId = ahtvCookieFrom(request).ahtvUserId().intValue();
        String controlCode = request.getParameter(CONTROL_CODE_PARAMETER_NAME);
        String controlCodeType = request.getParameter(CONTROL_CODE_TYPE_PARAMETER_NAME);
        
        ServiceResponse serviceResponse = controlCodeVerifier().forUser(ahtvUserId, controlCode, controlCodeType);

        return converter.toXML(serviceResponse);
    }

    protected ControlCodeVerifier controlCodeVerifier() throws MalformedURLException
    {
        return new ControlCodeVerifierImpl(ahtvUserService());
    }
}
