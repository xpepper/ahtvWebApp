
package it.matrix.services.userprofiledata;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserProfileData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserProfileData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="apc" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="cli" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="snrsType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="spc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserProfileData", namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", propOrder = {
    "apc",
    "cli",
    "password",
    "snrsType",
    "spc",
    "userType",
    "username"
})
public class UserProfileData {

    @XmlElementRef(name = "apc", namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", type = JAXBElement.class)
    protected JAXBElement<Long> apc;
    @XmlElementRef(name = "cli", namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", type = JAXBElement.class)
    protected JAXBElement<String> cli;
    @XmlElementRef(name = "password", namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", type = JAXBElement.class)
    protected JAXBElement<String> password;
    @XmlElementRef(name = "snrsType", namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", type = JAXBElement.class)
    protected JAXBElement<Integer> snrsType;
    @XmlElementRef(name = "spc", namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", type = JAXBElement.class)
    protected JAXBElement<String> spc;
    @XmlElementRef(name = "userType", namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", type = JAXBElement.class)
    protected JAXBElement<Integer> userType;
    @XmlElementRef(name = "username", namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", type = JAXBElement.class)
    protected JAXBElement<String> username;

    /**
     * Gets the value of the apc property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public JAXBElement<Long> getApc() {
        return apc;
    }

    /**
     * Sets the value of the apc property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public void setApc(JAXBElement<Long> value) {
        this.apc = ((JAXBElement<Long> ) value);
    }

    /**
     * Gets the value of the cli property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCli() {
        return cli;
    }

    /**
     * Sets the value of the cli property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCli(JAXBElement<String> value) {
        this.cli = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPassword(JAXBElement<String> value) {
        this.password = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the snrsType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getSnrsType() {
        return snrsType;
    }

    /**
     * Sets the value of the snrsType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setSnrsType(JAXBElement<Integer> value) {
        this.snrsType = ((JAXBElement<Integer> ) value);
    }

    /**
     * Gets the value of the spc property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSpc() {
        return spc;
    }

    /**
     * Sets the value of the spc property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSpc(JAXBElement<String> value) {
        this.spc = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the userType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getUserType() {
        return userType;
    }

    /**
     * Sets the value of the userType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setUserType(JAXBElement<Integer> value) {
        this.userType = ((JAXBElement<Integer> ) value);
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUsername(JAXBElement<String> value) {
        this.username = ((JAXBElement<String> ) value);
    }

}
