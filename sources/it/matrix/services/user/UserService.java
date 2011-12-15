package it.matrix.services.user;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

@WebServiceClient(name = "user-service", targetNamespace = "urn:aa.rossoalice.alice.it", wsdlLocation = "http://aa.rossoalice.alice.it/aa/services/user-service?wsdl")
public class UserService extends Service
{

    private final static URL USERSERVICE_WSDL_LOCATION;

    static
    {
        URL url = null;
        try
        {
            url = new URL("http://aa.rossoalice.alice.it/aa/services/user-service?wsdl");
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        USERSERVICE_WSDL_LOCATION = url;
    }

    public UserService(URL wsdlLocation, QName serviceName)
    {
        super(wsdlLocation, serviceName);
    }

    public UserService()
    {
        super(USERSERVICE_WSDL_LOCATION, new QName("urn:aa.rossoalice.alice.it", "user-service"));
    }

    public UserService(URL wsdlLocation)
    {
        this(wsdlLocation, new QName("urn:aa.rossoalice.alice.it", "user-service"));
    }

    @WebEndpoint(name = "user-serviceHttpPort")
    public UserServicePortType getUserServiceHttpPort()
    {
        return (UserServicePortType) super.getPort(new QName("urn:aa.rossoalice.alice.it", "user-serviceHttpPort"), UserServicePortType.class);
    }

}
