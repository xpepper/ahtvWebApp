package it.matrix.services.userprivacydata;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.*;

@WebServiceClient(name = "UserDPService", targetNamespace = "http://services.backoffice.matrix.it/", wsdlLocation = "http://dp.alice.it:4040/pdp-services/UserDPService?wsdl")
public class UserDPService extends Service
{

    private final static URL USERDPSERVICE_WSDL_LOCATION;

    static
    {
        URL url = null;
        try
        {
            url = new URL("http://dp.alice.it:4040/pdp-services/UserDPService?wsdl");
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        USERDPSERVICE_WSDL_LOCATION = url;
    }

    public UserDPService(URL wsdlLocation, QName serviceName)
    {
        super(wsdlLocation, serviceName);
    }

    public UserDPService()
    {
        super(USERDPSERVICE_WSDL_LOCATION, new QName("http://services.backoffice.matrix.it/", "UserDPService"));
    }

    public UserDPService(URL wsdlLocation)
    {
        this(wsdlLocation, new QName("http://services.backoffice.matrix.it/", "UserDPService"));
    }

    @WebEndpoint(name = "UserDPPort")
    public UserDP getUserDPPort()
    {
        return (UserDP) super.getPort(new QName("http://services.backoffice.matrix.it/", "UserDPPort"), UserDP.class);
    }

}
