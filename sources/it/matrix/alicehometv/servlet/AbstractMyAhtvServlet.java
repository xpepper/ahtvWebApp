package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.db.DbConnectionProvider;
import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.service.*;
import it.telecomitalia.rossoalice.aa.sandbox.constants.Parameters;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("serial")
public abstract class AbstractMyAhtvServlet extends AbstractAhtvServlet
{
    protected UserAuthenticationService userAuthenticationService() throws MalformedURLException
    {
        URL userServiceLocation = new URL(MyAhtvConfiguration.USER_SERVICE_WSDL_LOCATION());
        URL userProfileServiceLocation = new URL(MyAhtvConfiguration.USER_PROFILE_SERVICE_WSDL_LOCATION());

        ActivityLogger.debug("Trying to build user authentication service with userServiceLocation:" + userServiceLocation + " and userProfileServiceLocation:" + userProfileServiceLocation);
        return new UserAuthenticationServiceImpl(userServiceLocation, userProfileServiceLocation);
    }

    protected AhtvUserService ahtvUserService() throws MalformedURLException
    {
        URL userPrivacyServiceLocation = new URL(MyAhtvConfiguration.USER_PRIVACY_SERVICE_WSDL_LOCATION());
        DbConnectionProvider dbConnectionProvider = (DbConnectionProvider) getServletContext().getAttribute(DbConnectionProvider.ATTRIBUTE_NAME);

        ActivityLogger.debug("Trying to build user privacy profile service with userPrivacyServiceLocation:" + userPrivacyServiceLocation + " and dbConnectionProvider:" + dbConnectionProvider);
        return new AhtvUserServiceImpl(userPrivacyServiceLocation, dbConnectionProvider);
    }
    
    protected AhtvUserServiceFacade ahtvUserServiceFacade() throws MalformedURLException
    {
        return new AhtvUserServiceFacadeImpl(ahtvUserService(), userAuthenticationService());
    }

    protected String sunrisePassportCodeFrom(HttpServletRequest request)
    {
        String sunrisePassportCode = (String) request.getAttribute(Parameters.AASANDBOX_SPC);
        ActivityLogger.debug("Sunrise Passport Code: " + sunrisePassportCode);
        return sunrisePassportCode;
    }
}
