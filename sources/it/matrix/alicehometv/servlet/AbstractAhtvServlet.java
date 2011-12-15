package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.util.AhtvCookie;
import it.matrix.alicehometv.util.CookieValueDetector;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public abstract class AbstractAhtvServlet extends HttpServlet
{
    public final void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            logDataFrom(request);
            super.service(request, response);
        }
        catch (Throwable aThrowable)
        {
            ActivityLogger.logException(aThrowable);
            response.sendRedirect("http://aa.rossoalice.alice.it/aa-logout/logout?finalurlok=http://" + request.getServerName() + ":" + request.getServerPort() + "/Errore/redirectToErrorPage.html");
        }
    }

    protected AhtvCookie ahtvCookieFrom(HttpServletRequest request)
    {
        return new CookieValueDetector(request.getCookies()).detectAhtvCookie();
    }

    private void logDataFrom(HttpServletRequest request)
    {
        ActivityLogger.logParametersFrom(request);
        ActivityLogger.info("Ahtv cookie value = " + ahtvCookieFrom(request).clearedValue());
    }
}
