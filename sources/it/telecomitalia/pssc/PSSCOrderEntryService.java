
package it.telecomitalia.pssc;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "PSSCOrderEntryService", targetNamespace = "http://tempuri.org/", wsdlLocation = "")
public class PSSCOrderEntryService
    extends Service
{


    public PSSCOrderEntryService(URL wsdlLocation) {
        super(wsdlLocation, new QName("http://tempuri.org/", "PSSCOrderEntryService"));
    }


    @WebEndpoint(name = "PSSCOrderEntryServiceSoap")
    public PSSCOrderEntryServiceSoap getPSSCOrderEntryServiceSoap() {
        return (PSSCOrderEntryServiceSoap)super.getPort(new QName("http://tempuri.org/", "PSSCOrderEntryServiceSoap"), PSSCOrderEntryServiceSoap.class);
    }


    @WebEndpoint(name = "PSSCOrderEntryServiceSoap")
    public PSSCOrderEntryServiceSoap getPSSCOrderEntryServiceSoap(WebServiceFeature... features) {
        return (PSSCOrderEntryServiceSoap)super.getPort(new QName("http://tempuri.org/", "PSSCOrderEntryServiceSoap"), PSSCOrderEntryServiceSoap.class, features);
    }

}
