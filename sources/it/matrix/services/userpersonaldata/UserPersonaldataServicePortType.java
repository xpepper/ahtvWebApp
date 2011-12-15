
package it.matrix.services.userpersonaldata;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAXWS SI.
 * JAX-WS RI 2.0_02-b08-fcs
 * Generated source version: 2.0
 * 
 */
@WebService(name = "user-personaldata-servicePortType", targetNamespace = "urn:aa.rossoalice.alice.it")
public interface UserPersonaldataServicePortType {


    /**
     * 
     * @param spc
     * @return
     *     returns it.matrix.services.userpersonaldata.UserPersonalData
     * @throws UserNotFoundException_Exception
     */
    @WebMethod
    @WebResult(name = "out", targetNamespace = "urn:aa.rossoalice.alice.it")
    @RequestWrapper(localName = "getUserPersonalDataFromSpc", targetNamespace = "urn:aa.rossoalice.alice.it", className = "it.matrix.services.userpersonaldata.GetUserPersonalDataFromSpc")
    @ResponseWrapper(localName = "getUserPersonalDataFromSpcResponse", targetNamespace = "urn:aa.rossoalice.alice.it", className = "it.matrix.services.userpersonaldata.GetUserPersonalDataFromSpcResponse")
    public UserPersonalData getUserPersonalDataFromSpc(
        @WebParam(name = "spc", targetNamespace = "urn:aa.rossoalice.alice.it")
        String spc)
        throws UserNotFoundException_Exception
    ;

}