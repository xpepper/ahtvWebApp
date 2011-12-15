
package it.matrix.services.userprofiledata;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.matrix.services.userprofiledata package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UserNotFoundException_QNAME = new QName("urn:aa.rossoalice.alice.it", "UserNotFoundException");
    private final static QName _UserProfileDataUsername_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "username");
    private final static QName _UserProfileDataSnrsType_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "snrsType");
    private final static QName _UserProfileDataApc_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "apc");
    private final static QName _UserProfileDataCli_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "cli");
    private final static QName _UserProfileDataUserType_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "userType");
    private final static QName _UserProfileDataSpc_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "spc");
    private final static QName _UserProfileDataPassword_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "password");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.matrix.services.userprofiledata
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UserNotFoundException }
     * 
     */
    public UserNotFoundException createUserNotFoundException() {
        return new UserNotFoundException();
    }

    /**
     * Create an instance of {@link GetSpcFromApc }
     * 
     */
    public GetSpcFromApc createGetSpcFromApc() {
        return new GetSpcFromApc();
    }

    /**
     * Create an instance of {@link GetUserFromCliResponse }
     * 
     */
    public GetUserFromCliResponse createGetUserFromCliResponse() {
        return new GetUserFromCliResponse();
    }

    /**
     * Create an instance of {@link IsAliceHomeTvUserFromSpc }
     * 
     */
    public IsAliceHomeTvUserFromSpc createIsAliceHomeTvUserFromSpc() {
        return new IsAliceHomeTvUserFromSpc();
    }

    /**
     * Create an instance of {@link IsAliceHomeTvUserFromSpcResponse }
     * 
     */
    public IsAliceHomeTvUserFromSpcResponse createIsAliceHomeTvUserFromSpcResponse() {
        return new IsAliceHomeTvUserFromSpcResponse();
    }

    /**
     * Create an instance of {@link GetSpcFromApcResponse }
     * 
     */
    public GetSpcFromApcResponse createGetSpcFromApcResponse() {
        return new GetSpcFromApcResponse();
    }

    /**
     * Create an instance of {@link GetUserFromApcResponse }
     * 
     */
    public GetUserFromApcResponse createGetUserFromApcResponse() {
        return new GetUserFromApcResponse();
    }

    /**
     * Create an instance of {@link UserProfileData }
     * 
     */
    public UserProfileData createUserProfileData() {
        return new UserProfileData();
    }

    /**
     * Create an instance of {@link GetUserFromSpc }
     * 
     */
    public GetUserFromSpc createGetUserFromSpc() {
        return new GetUserFromSpc();
    }

    /**
     * Create an instance of {@link GetUserFromApc }
     * 
     */
    public GetUserFromApc createGetUserFromApc() {
        return new GetUserFromApc();
    }

    /**
     * Create an instance of {@link GetUserFromCli }
     * 
     */
    public GetUserFromCli createGetUserFromCli() {
        return new GetUserFromCli();
    }

    /**
     * Create an instance of {@link GetApcFromSpc }
     * 
     */
    public GetApcFromSpc createGetApcFromSpc() {
        return new GetApcFromSpc();
    }

    /**
     * Create an instance of {@link GetUserFromSpcResponse }
     * 
     */
    public GetUserFromSpcResponse createGetUserFromSpcResponse() {
        return new GetUserFromSpcResponse();
    }

    /**
     * Create an instance of {@link GetApcFromSpcResponse }
     * 
     */
    public GetApcFromSpcResponse createGetApcFromSpcResponse() {
        return new GetApcFromSpcResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:aa.rossoalice.alice.it", name = "UserNotFoundException")
    public JAXBElement<UserNotFoundException> createUserNotFoundException(UserNotFoundException value) {
        return new JAXBElement<UserNotFoundException>(_UserNotFoundException_QNAME, UserNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "username", scope = UserProfileData.class)
    public JAXBElement<String> createUserProfileDataUsername(String value) {
        return new JAXBElement<String>(_UserProfileDataUsername_QNAME, String.class, UserProfileData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "snrsType", scope = UserProfileData.class)
    public JAXBElement<Integer> createUserProfileDataSnrsType(Integer value) {
        return new JAXBElement<Integer>(_UserProfileDataSnrsType_QNAME, Integer.class, UserProfileData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "apc", scope = UserProfileData.class)
    public JAXBElement<Long> createUserProfileDataApc(Long value) {
        return new JAXBElement<Long>(_UserProfileDataApc_QNAME, Long.class, UserProfileData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "cli", scope = UserProfileData.class)
    public JAXBElement<String> createUserProfileDataCli(String value) {
        return new JAXBElement<String>(_UserProfileDataCli_QNAME, String.class, UserProfileData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "userType", scope = UserProfileData.class)
    public JAXBElement<Integer> createUserProfileDataUserType(Integer value) {
        return new JAXBElement<Integer>(_UserProfileDataUserType_QNAME, Integer.class, UserProfileData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "spc", scope = UserProfileData.class)
    public JAXBElement<String> createUserProfileDataSpc(String value) {
        return new JAXBElement<String>(_UserProfileDataSpc_QNAME, String.class, UserProfileData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "password", scope = UserProfileData.class)
    public JAXBElement<String> createUserProfileDataPassword(String value) {
        return new JAXBElement<String>(_UserProfileDataPassword_QNAME, String.class, UserProfileData.class, value);
    }

}
