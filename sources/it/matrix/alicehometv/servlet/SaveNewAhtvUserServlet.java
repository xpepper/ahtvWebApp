package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.service.AhtvUserServiceFacade;
import it.matrix.alicehometv.util.AhtvCookie;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class SaveNewAhtvUserServlet extends AbstractUpdateAhtvUserServlet
{
    protected boolean saveOrUpdateWith(String emailAddress, String mobileNumber, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException
    {
        AhtvUserServiceFacade userServiceFacade = ahtvUserServiceFacade();

        int generatedId = userServiceFacade.saveNewUserWith(sunrisePassportCodeFrom(request), emailAddress, mobileNumber);
        
        boolean savedSuccessfully = userServiceFacade.isAValidId(generatedId);
        if (savedSuccessfully)
        {
            AhtvCookie ahtvCookie = ahtvCookieFrom(request);
            ahtvCookie.addAhtvUserId(generatedId);
            response.addCookie(ahtvCookie);
        }
        
        return savedSuccessfully;
    }
}
