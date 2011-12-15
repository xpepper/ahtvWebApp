package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.*;
import it.matrix.alicehometv.service.AhtvUserService;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class EmailControlCodeCheckServlet extends AbstractMyAhtvServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        int ahtvUserId = ahtvCookieFrom(request).ahtvUserId().intValue();
        String controlCode = request.getParameter(ControlCodeCheckServlet.CONTROL_CODE_PARAMETER_NAME);
        
        ServiceResponse serviceResponse = controlCodeVerifier().forUser(ahtvUserId, controlCode, AhtvUserService.CONTROL_CODE_FOR_EMAIL);
        if (serviceResponse.isOk())
            response.sendRedirect(request.getContextPath() + "/MyHTV/avvisi_recapiti.html");
        else
            response.sendRedirect("http://" + request.getServerName() + ":" + request.getServerPort() + "/Errore/index.html?Err=CONTROL_CODE_ERROR");
    }

    protected ControlCodeVerifier controlCodeVerifier() throws MalformedURLException
    {
        return new ControlCodeVerifierImpl(ahtvUserService());
    }
}
