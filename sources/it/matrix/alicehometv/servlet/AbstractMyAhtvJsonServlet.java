package it.matrix.alicehometv.servlet;

import static it.matrix.alicehometv.servlet.MyAhtvConfiguration.*;
import it.matrix.alicehometv.ServiceResponse;
import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.profile.iptv.IPTVUserProfileBroker;
import it.matrix.alicehometv.profile.iptv.IPTVUserProfileBrokerImpl;
import it.matrix.alicehometv.util.TimeProviderImpl;
import it.telecomitalia.pssc.PSSCOrderEntryService;
import it.telecomitalia.pssc.PSSCOrderEntryServiceSoap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

@SuppressWarnings("serial")
public abstract class AbstractMyAhtvJsonServlet extends AbstractMyAhtvServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
    {
        executeWith(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        executeWith(request, response);
    }

    protected abstract String runOn(XStream converter, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException;

    private void executeWith(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String serviceResponse;
        XStream converter = new XStream(new JettisonMappedXmlDriver());
        converter.alias("responseMessage", ServiceResponse.class);
        try
        {
            if (ahtvCookieFrom(request).isIptvUser())
                serviceResponse = runOn(converter, request, response);
            else
                serviceResponse = converter.toXML(ServiceResponse.errorResponseWith("not an iptv user"));

        }
        catch (Throwable throwable)
        {
            ActivityLogger.fatal("Cannot execute the requested service");
            ActivityLogger.logException(throwable);
            serviceResponse = converter.toXML(ServiceResponse.errorResponseWith("A generic error occurred"));
        }
        response.getWriter().write(serviceResponse);
    }

	protected IPTVUserProfileBroker iptvUserProfileBroker() throws MalformedURLException 
	{
	    ActivityLogger.debug("Trying to IPTV User Profile Broker with URL service location:" + URL_PSSC_SET_IPTV_PROFILE() + " and URL Http For IPTV Profile Finder:" + URL_PSSC_PERSONAL_DATA());
	    PSSCOrderEntryServiceSoap psscOrderExecutor = new PSSCOrderEntryService(new URL(URL_PSSC_SET_IPTV_PROFILE())).getPSSCOrderEntryServiceSoap();
        return new IPTVUserProfileBrokerImpl(URL_PSSC_PERSONAL_DATA(), psscOrderExecutor, new TimeProviderImpl()); 
	}
}
