
package it.matrix.services.userprivacydata;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.matrix.services.userprivacydata package. 
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

    private final static QName _ModifyUserEmail_QNAME = new QName("http://services.backoffice.matrix.it/", "modifyUserEmail");
    private final static QName _InsertUserResponse_QNAME = new QName("http://services.backoffice.matrix.it/", "insertUserResponse");
    private final static QName _LoadUserByIntID_QNAME = new QName("http://services.backoffice.matrix.it/", "loadUserByIntID");
    private final static QName _LoadPhoneFromID_QNAME = new QName("http://services.backoffice.matrix.it/", "loadPhoneFromID");
    private final static QName _ModifyUserPhone_QNAME = new QName("http://services.backoffice.matrix.it/", "modifyUserPhone");
    private final static QName _InsertUser_QNAME = new QName("http://services.backoffice.matrix.it/", "insertUser");
    private final static QName _ModifyUserEmailResponse_QNAME = new QName("http://services.backoffice.matrix.it/", "modifyUserEmailResponse");
    private final static QName _LoadEmailFromIntID_QNAME = new QName("http://services.backoffice.matrix.it/", "loadEmailFromIntID");
    private final static QName _LoadPhoneFromIDResponse_QNAME = new QName("http://services.backoffice.matrix.it/", "loadPhoneFromIDResponse");
    private final static QName _LoadUserByExtID_QNAME = new QName("http://services.backoffice.matrix.it/", "loadUserByExtID");
    private final static QName _LoadUserByExtIDResponse_QNAME = new QName("http://services.backoffice.matrix.it/", "loadUserByExtIDResponse");
    private final static QName _LoadUserByIntIDResponse_QNAME = new QName("http://services.backoffice.matrix.it/", "loadUserByIntIDResponse");
    private final static QName _LoadEmailFromIntIDResponse_QNAME = new QName("http://services.backoffice.matrix.it/", "loadEmailFromIntIDResponse");
    private final static QName _ModifyUserPhoneResponse_QNAME = new QName("http://services.backoffice.matrix.it/", "modifyUserPhoneResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.matrix.services.userprivacydata
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UserID }
     * 
     */
    public UserID createUserID() {
        return new UserID();
    }

    /**
     * Create an instance of {@link ModifyUserPhoneResponse }
     * 
     */
    public ModifyUserPhoneResponse createModifyUserPhoneResponse() {
        return new ModifyUserPhoneResponse();
    }

    /**
     * Create an instance of {@link ModifyUserEmail }
     * 
     */
    public ModifyUserEmail createModifyUserEmail() {
        return new ModifyUserEmail();
    }

    /**
     * Create an instance of {@link InsertUser }
     * 
     */
    public InsertUser createInsertUser() {
        return new InsertUser();
    }

    /**
     * Create an instance of {@link ModifyUserPhone }
     * 
     */
    public ModifyUserPhone createModifyUserPhone() {
        return new ModifyUserPhone();
    }

    /**
     * Create an instance of {@link LoadUserByIntIDResponse }
     * 
     */
    public LoadUserByIntIDResponse createLoadUserByIntIDResponse() {
        return new LoadUserByIntIDResponse();
    }

    /**
     * Create an instance of {@link LoadEmailFromIntID }
     * 
     */
    public LoadEmailFromIntID createLoadEmailFromIntID() {
        return new LoadEmailFromIntID();
    }

    /**
     * Create an instance of {@link LoadUserByIntID }
     * 
     */
    public LoadUserByIntID createLoadUserByIntID() {
        return new LoadUserByIntID();
    }

    /**
     * Create an instance of {@link InsertUserResponse }
     * 
     */
    public InsertUserResponse createInsertUserResponse() {
        return new InsertUserResponse();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link EmailResponse }
     * 
     */
    public EmailResponse createEmailResponse() {
        return new EmailResponse();
    }

    /**
     * Create an instance of {@link PhoneResponse }
     * 
     */
    public PhoneResponse createPhoneResponse() {
        return new PhoneResponse();
    }

    /**
     * Create an instance of {@link ResultResponse }
     * 
     */
    public ResultResponse createResultResponse() {
        return new ResultResponse();
    }

    /**
     * Create an instance of {@link LoadUserByExtIDResponse }
     * 
     */
    public LoadUserByExtIDResponse createLoadUserByExtIDResponse() {
        return new LoadUserByExtIDResponse();
    }

    /**
     * Create an instance of {@link LoadPhoneFromIDResponse }
     * 
     */
    public LoadPhoneFromIDResponse createLoadPhoneFromIDResponse() {
        return new LoadPhoneFromIDResponse();
    }

    /**
     * Create an instance of {@link LoadEmailFromIntIDResponse }
     * 
     */
    public LoadEmailFromIntIDResponse createLoadEmailFromIntIDResponse() {
        return new LoadEmailFromIntIDResponse();
    }

    /**
     * Create an instance of {@link UserPDP }
     * 
     */
    public UserPDP createUserPDP() {
        return new UserPDP();
    }

    /**
     * Create an instance of {@link BaseType }
     * 
     */
    public BaseType createBaseType() {
        return new BaseType();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link ModifyUserEmailResponse }
     * 
     */
    public ModifyUserEmailResponse createModifyUserEmailResponse() {
        return new ModifyUserEmailResponse();
    }

    /**
     * Create an instance of {@link Email }
     * 
     */
    public Email createEmail() {
        return new Email();
    }

    /**
     * Create an instance of {@link LoadPhoneFromID }
     * 
     */
    public LoadPhoneFromID createLoadPhoneFromID() {
        return new LoadPhoneFromID();
    }

    /**
     * Create an instance of {@link UserAnag }
     * 
     */
    public UserAnag createUserAnag() {
        return new UserAnag();
    }

    /**
     * Create an instance of {@link Phone }
     * 
     */
    public Phone createPhone() {
        return new Phone();
    }

    /**
     * Create an instance of {@link LoadUserByExtID }
     * 
     */
    public LoadUserByExtID createLoadUserByExtID() {
        return new LoadUserByExtID();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUserEmail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.backoffice.matrix.it/", name = "modifyUserEmail")
    public JAXBElement<ModifyUserEmail> createModifyUserEmail(ModifyUserEmail value) {
        return new JAXBElement<ModifyUserEmail>(_ModifyUserEmail_QNAME, ModifyUserEmail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.backoffice.matrix.it/", name = "insertUserResponse")
    public JAXBElement<InsertUserResponse> createInsertUserResponse(InsertUserResponse value) {
        return new JAXBElement<InsertUserResponse>(_InsertUserResponse_QNAME, InsertUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadUserByIntID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.backoffice.matrix.it/", name = "loadUserByIntID")
    public JAXBElement<LoadUserByIntID> createLoadUserByIntID(LoadUserByIntID value) {
        return new JAXBElement<LoadUserByIntID>(_LoadUserByIntID_QNAME, LoadUserByIntID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadPhoneFromID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.backoffice.matrix.it/", name = "loadPhoneFromID")
    public JAXBElement<LoadPhoneFromID> createLoadPhoneFromID(LoadPhoneFromID value) {
        return new JAXBElement<LoadPhoneFromID>(_LoadPhoneFromID_QNAME, LoadPhoneFromID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUserPhone }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.backoffice.matrix.it/", name = "modifyUserPhone")
    public JAXBElement<ModifyUserPhone> createModifyUserPhone(ModifyUserPhone value) {
        return new JAXBElement<ModifyUserPhone>(_ModifyUserPhone_QNAME, ModifyUserPhone.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.backoffice.matrix.it/", name = "insertUser")
    public JAXBElement<InsertUser> createInsertUser(InsertUser value) {
        return new JAXBElement<InsertUser>(_InsertUser_QNAME, InsertUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUserEmailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.backoffice.matrix.it/", name = "modifyUserEmailResponse")
    public JAXBElement<ModifyUserEmailResponse> createModifyUserEmailResponse(ModifyUserEmailResponse value) {
        return new JAXBElement<ModifyUserEmailResponse>(_ModifyUserEmailResponse_QNAME, ModifyUserEmailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadEmailFromIntID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.backoffice.matrix.it/", name = "loadEmailFromIntID")
    public JAXBElement<LoadEmailFromIntID> createLoadEmailFromIntID(LoadEmailFromIntID value) {
        return new JAXBElement<LoadEmailFromIntID>(_LoadEmailFromIntID_QNAME, LoadEmailFromIntID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadPhoneFromIDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.backoffice.matrix.it/", name = "loadPhoneFromIDResponse")
    public JAXBElement<LoadPhoneFromIDResponse> createLoadPhoneFromIDResponse(LoadPhoneFromIDResponse value) {
        return new JAXBElement<LoadPhoneFromIDResponse>(_LoadPhoneFromIDResponse_QNAME, LoadPhoneFromIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadUserByExtID }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.backoffice.matrix.it/", name = "loadUserByExtID")
    public JAXBElement<LoadUserByExtID> createLoadUserByExtID(LoadUserByExtID value) {
        return new JAXBElement<LoadUserByExtID>(_LoadUserByExtID_QNAME, LoadUserByExtID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadUserByExtIDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.backoffice.matrix.it/", name = "loadUserByExtIDResponse")
    public JAXBElement<LoadUserByExtIDResponse> createLoadUserByExtIDResponse(LoadUserByExtIDResponse value) {
        return new JAXBElement<LoadUserByExtIDResponse>(_LoadUserByExtIDResponse_QNAME, LoadUserByExtIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadUserByIntIDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.backoffice.matrix.it/", name = "loadUserByIntIDResponse")
    public JAXBElement<LoadUserByIntIDResponse> createLoadUserByIntIDResponse(LoadUserByIntIDResponse value) {
        return new JAXBElement<LoadUserByIntIDResponse>(_LoadUserByIntIDResponse_QNAME, LoadUserByIntIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoadEmailFromIntIDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.backoffice.matrix.it/", name = "loadEmailFromIntIDResponse")
    public JAXBElement<LoadEmailFromIntIDResponse> createLoadEmailFromIntIDResponse(LoadEmailFromIntIDResponse value) {
        return new JAXBElement<LoadEmailFromIntIDResponse>(_LoadEmailFromIntIDResponse_QNAME, LoadEmailFromIntIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUserPhoneResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.backoffice.matrix.it/", name = "modifyUserPhoneResponse")
    public JAXBElement<ModifyUserPhoneResponse> createModifyUserPhoneResponse(ModifyUserPhoneResponse value) {
        return new JAXBElement<ModifyUserPhoneResponse>(_ModifyUserPhoneResponse_QNAME, ModifyUserPhoneResponse.class, null, value);
    }

}
