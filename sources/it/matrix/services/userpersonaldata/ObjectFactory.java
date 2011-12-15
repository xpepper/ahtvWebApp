
package it.matrix.services.userpersonaldata;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.matrix.services.userpersonaldata package. 
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
    private final static QName _UserPersonalDataUsername_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "username");
    private final static QName _UserPersonalDataName_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "name");
    private final static QName _UserPersonalDataAddress_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "address");
    private final static QName _UserPersonalDataEmailAddress_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "emailAddress");
    private final static QName _UserPersonalDataMobilePhoneNumber_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "mobilePhoneNumber");
    private final static QName _NameFName_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "FName");
    private final static QName _NameLName_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "LName");
    private final static QName _AddressZipCode_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "zipCode");
    private final static QName _AddressStreet_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "street");
    private final static QName _AddressProvince_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "province");
    private final static QName _AddressCountry_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "country");
    private final static QName _AddressCity_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "city");
    private final static QName _StreetPrefix_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "prefix");
    private final static QName _StreetNumber_QNAME = new QName("http://types.detail.ws.rossoalice.telecomitalia.it", "number");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.matrix.services.userpersonaldata
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
     * Create an instance of {@link UserPersonalData }
     * 
     */
    public UserPersonalData createUserPersonalData() {
        return new UserPersonalData();
    }

    /**
     * Create an instance of {@link GetUserPersonalDataFromSpcResponse }
     * 
     */
    public GetUserPersonalDataFromSpcResponse createGetUserPersonalDataFromSpcResponse() {
        return new GetUserPersonalDataFromSpcResponse();
    }

    /**
     * Create an instance of {@link Name }
     * 
     */
    public Name createName() {
        return new Name();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link Street }
     * 
     */
    public Street createStreet() {
        return new Street();
    }

    /**
     * Create an instance of {@link GetUserPersonalDataFromSpc }
     * 
     */
    public GetUserPersonalDataFromSpc createGetUserPersonalDataFromSpc() {
        return new GetUserPersonalDataFromSpc();
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
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "username", scope = UserPersonalData.class)
    public JAXBElement<String> createUserPersonalDataUsername(String value) {
        return new JAXBElement<String>(_UserPersonalDataUsername_QNAME, String.class, UserPersonalData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Name }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "name", scope = UserPersonalData.class)
    public JAXBElement<Name> createUserPersonalDataName(Name value) {
        return new JAXBElement<Name>(_UserPersonalDataName_QNAME, Name.class, UserPersonalData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Address }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "address", scope = UserPersonalData.class)
    public JAXBElement<Address> createUserPersonalDataAddress(Address value) {
        return new JAXBElement<Address>(_UserPersonalDataAddress_QNAME, Address.class, UserPersonalData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "emailAddress", scope = UserPersonalData.class)
    public JAXBElement<String> createUserPersonalDataEmailAddress(String value) {
        return new JAXBElement<String>(_UserPersonalDataEmailAddress_QNAME, String.class, UserPersonalData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "mobilePhoneNumber", scope = UserPersonalData.class)
    public JAXBElement<String> createUserPersonalDataMobilePhoneNumber(String value) {
        return new JAXBElement<String>(_UserPersonalDataMobilePhoneNumber_QNAME, String.class, UserPersonalData.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "FName", scope = Name.class)
    public JAXBElement<String> createNameFName(String value) {
        return new JAXBElement<String>(_NameFName_QNAME, String.class, Name.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "LName", scope = Name.class)
    public JAXBElement<String> createNameLName(String value) {
        return new JAXBElement<String>(_NameLName_QNAME, String.class, Name.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "zipCode", scope = Address.class)
    public JAXBElement<String> createAddressZipCode(String value) {
        return new JAXBElement<String>(_AddressZipCode_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Street }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "street", scope = Address.class)
    public JAXBElement<Street> createAddressStreet(Street value) {
        return new JAXBElement<Street>(_AddressStreet_QNAME, Street.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "province", scope = Address.class)
    public JAXBElement<String> createAddressProvince(String value) {
        return new JAXBElement<String>(_AddressProvince_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "country", scope = Address.class)
    public JAXBElement<String> createAddressCountry(String value) {
        return new JAXBElement<String>(_AddressCountry_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "city", scope = Address.class)
    public JAXBElement<String> createAddressCity(String value) {
        return new JAXBElement<String>(_AddressCity_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "prefix", scope = Street.class)
    public JAXBElement<String> createStreetPrefix(String value) {
        return new JAXBElement<String>(_StreetPrefix_QNAME, String.class, Street.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "address", scope = Street.class)
    public JAXBElement<String> createStreetAddress(String value) {
        return new JAXBElement<String>(_UserPersonalDataAddress_QNAME, String.class, Street.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", name = "number", scope = Street.class)
    public JAXBElement<String> createStreetNumber(String value) {
        return new JAXBElement<String>(_StreetNumber_QNAME, String.class, Street.class, value);
    }

}
