package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.logger.ActivityLogger;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class UpdateAhtvUserServlet extends AbstractUpdateAhtvUserServlet
{
    protected boolean saveOrUpdateWith(String emailAddress, String mobileNumber, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException
    {
        Integer ahtvUserId = ahtvCookieFrom(request).ahtvUserId();
        ActivityLogger.info("Updating user with id:" + ahtvUserId + " with new email:" + emailAddress + " and new mobile:" + mobileNumber);
        return ahtvUserServiceFacade().updateUserWith(ahtvUserId, emailAddress, mobileNumber);
    }
}
