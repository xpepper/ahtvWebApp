package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.AhtvUser;
import it.matrix.alicehometv.UserProfile;
import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.service.*;
import it.matrix.alicehometv.util.AhtvCookie;
import it.telecomitalia.rossoalice.aa.sandbox.constants.Parameters;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AuthorizeController extends AbstractMyAhtvServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String sunrisePassportCode = sunrisePassportCodeFrom(request);
        String currentSunriseSessionId = sunriseSessionIdFrom(request);

        AhtvUserService ahtvUserService = ahtvUserService();
        
        boolean isFirstLogin = false;
        AhtvCookie ahtvCookie = ahtvCookieFrom(request);
        if (ahtvCookie.isExpiredFor(currentSunriseSessionId))
        {
            isFirstLogin = true;
            UserAuthenticationService authenticationService = userAuthenticationService();
            UserProfile userProfile = authenticationService.userProfileFor(sunrisePassportCode);
            boolean isIptvUser = authenticationService.isIptvUser(sunrisePassportCode);
            
            ahtvUserServiceFacade(ahtvUserService, authenticationService).checkForSunriseProfileDataUpdateFor(userProfile);

            ahtvCookie = createNewAhtvCookieWith(userProfile, isIptvUser, sunrisePassportCode, currentSunriseSessionId);
            response.addCookie(ahtvCookie);
        }
        
        AhtvUser ahtvUser = ahtvUserService.findUserBySPC(sunrisePassportCode);
        addAhtvUserIdToCookie(ahtvUser, ahtvCookie, response);
            
        prepareRequestWith(ahtvUser, ahtvCookie, isFirstLogin, request);
        forward("/templates/logged.jsp", request, response);
    }

    protected AhtvUserServiceFacade ahtvUserServiceFacade(AhtvUserService ahtvUserService, UserAuthenticationService userAuthenticationService) throws MalformedURLException
    {
        return new AhtvUserServiceFacadeImpl(ahtvUserService, userAuthenticationService);
    }
    
    private void prepareRequestWith(AhtvUser ahtvUser, AhtvCookie ahtvCookie, boolean isFirstLogin, HttpServletRequest request)
    {
        request.setAttribute("isIptvUser", ahtvCookie.isIptvUser());
        request.setAttribute("isNewUser", ahtvUser.isUnknown());
        request.setAttribute("hasPendingData", ahtvUser.hasPendingData());
        request.setAttribute("userProfileType", ahtvCookie.get(AhtvCookie.USER_PROFILE_TYPE_KEY));
        request.setAttribute("userName", ahtvCookie.get(AhtvCookie.USERNAME_KEY));
        request.setAttribute("isFirstLogin", isFirstLogin);
    }

    private void addAhtvUserIdToCookie(AhtvUser ahtvUser, AhtvCookie ahtvCookie, HttpServletResponse response)
    {
        if (ahtvUser.exists() && ahtvCookie.hasNoAhtvUserId())
        {
            ActivityLogger.debug("Adding ahtv user id:" + ahtvUser.ahtvId() + " to ahtv cookie: " + ahtvCookie.clearedValue());
            ahtvCookie.addAhtvUserId(ahtvUser.ahtvId());
            response.addCookie(ahtvCookie);
        }
    }

    private String sunriseSessionIdFrom(HttpServletRequest request)
    {
        String sunriseSessionId = (String) request.getAttribute(Parameters.AASANDBOX_SPC_TOKEN); 
        ActivityLogger.debug("sunrise session id:" + sunriseSessionId);    
        return sunriseSessionId;
    }

    private AhtvCookie createNewAhtvCookieWith(UserProfile userProfile, boolean isIptvUser, String sunrisePassportCode, String sunriseSessionId)
    {
        ActivityLogger.debug("Creating new cookie for " + userProfile + " with sunrise session id:" + sunriseSessionId);
        
        AhtvCookie ahtvCookie = new AhtvCookie();
        ahtvCookie.sunrisePassportCode(sunrisePassportCode);
        ahtvCookie.put(AhtvCookie.USERNAME_KEY, userProfile.username());
        ahtvCookie.put(AhtvCookie.EMAIL_KEY, userProfile.email());
        ahtvCookie.put(AhtvCookie.IPTV_USER_FLAG_KEY, Boolean.toString(isIptvUser));
        ahtvCookie.put(AhtvCookie.USER_PROFILE_TYPE_KEY, userProfile.typeAsString());
        ahtvCookie.put(AhtvCookie.CLI_KEY, userProfile.cli());
        ahtvCookie.put(AhtvCookie.SUNRISE_SESSION_ID_KEY, sunriseSessionId);
        
        return ahtvCookie;
    }

    private void forward(String target, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.getRequestDispatcher(target).forward(request, response);
    }
}
