package it.matrix.alicehometv.servlet;

import it.matrix.alicehometv.logger.ActivityLogger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class MyAhtvConfiguration extends HttpServlet
{
    private static final String CLIENT_DEFAULT_READ_TIMEOUT = "sun.net.client.defaultReadTimeout";
    private static final String CLIENT_DEFAULT_CONNECT_TIMEOUT = "sun.net.client.defaultConnectTimeout";
    
    private static String USER_SERVICE_WSDL_LOCATION;
    private static String USER_PROFILE_SERVICE_WSDL_LOCATION;
    private static String USER_PRIVACY_SERVICE_WSDL_LOCATION;
    private static String URL_PSSC_PURCHASE_LIST;
    private static String URL_PSSC_PERSONAL_DATA;
    private static String URL_PSSC_SET_IPTV_PROFILE;
    
    @Override
    public void init() throws ServletException 
    {
        super.init();
        
        configureServiceLocations();
        configureClientNetworkTimeouts();
    }

    private void configureClientNetworkTimeouts()
    {
        System.setProperty(CLIENT_DEFAULT_CONNECT_TIMEOUT, getInitParameter("DEFAULT_CONNECTION_TIMEOUT_IN_MILLIS"));
        System.setProperty(CLIENT_DEFAULT_READ_TIMEOUT, getInitParameter("DEFAULT_READ_TIMEOUT_IN_MILLIS"));
        
        ActivityLogger.info("Set property sun.net.client.defaultConnectTimeout: " + System.getProperty(CLIENT_DEFAULT_CONNECT_TIMEOUT) + " millis");
        ActivityLogger.info("Set property sun.net.client.defaultReadTimeout: " + System.getProperty(CLIENT_DEFAULT_READ_TIMEOUT) + " millis");
    }

    private void configureServiceLocations()
    {
        USER_SERVICE_WSDL_LOCATION = getInitParameter("USER_SERVICE_WSDL_LOCATION");
        USER_PROFILE_SERVICE_WSDL_LOCATION = getInitParameter("USER_PROFILE_SERVICE_WSDL_LOCATION");
        USER_PRIVACY_SERVICE_WSDL_LOCATION = getInitParameter("USER_PRIVACY_SERVICE_WSDL_LOCATION");
        URL_PSSC_PURCHASE_LIST = getInitParameter("URL_PSSC_PURCHASE_LIST");
        URL_PSSC_PERSONAL_DATA = getInitParameter("URL_PSSC_PERSONAL_DATA");
        URL_PSSC_SET_IPTV_PROFILE = getInitParameter("URL_PSSC_SET_IPTV_PROFILE");
        
        ActivityLogger.info("Loaded USER_SERVICE_WSDL_LOCATION: " + USER_SERVICE_WSDL_LOCATION);
        ActivityLogger.info("Loaded USER_PROFILE_SERVICE_WSDL_LOCATION: " + USER_PROFILE_SERVICE_WSDL_LOCATION);
        ActivityLogger.info("Loaded USER_PRIVACY_SERVICE_WSDL_LOCATION: " + USER_PRIVACY_SERVICE_WSDL_LOCATION);
        ActivityLogger.info("Loaded URL_PSSC_PURCHASE_LIST: " + URL_PSSC_PURCHASE_LIST);
        ActivityLogger.info("Loaded URL_PSSC_PERSONAL_DATA: " + URL_PSSC_PERSONAL_DATA);
        ActivityLogger.info("Loaded URL_PSSC_SET_IPTV_PROFILE: " + URL_PSSC_SET_IPTV_PROFILE);
    }

    public static String USER_SERVICE_WSDL_LOCATION()
    {
        return USER_SERVICE_WSDL_LOCATION;
    }

    public static String USER_PROFILE_SERVICE_WSDL_LOCATION()
    {
        return USER_PROFILE_SERVICE_WSDL_LOCATION;
    }

    public static String USER_PRIVACY_SERVICE_WSDL_LOCATION()
    {
        return USER_PRIVACY_SERVICE_WSDL_LOCATION;
    }
    
    public static String URL_PSSC_PURCHASE_LIST()
    {
        return URL_PSSC_PURCHASE_LIST;
    }
    
     public static String URL_PSSC_PERSONAL_DATA()
    {
        return URL_PSSC_PERSONAL_DATA;
    }

	public static String URL_PSSC_SET_IPTV_PROFILE() 
	{
		return URL_PSSC_SET_IPTV_PROFILE;
	}
}
