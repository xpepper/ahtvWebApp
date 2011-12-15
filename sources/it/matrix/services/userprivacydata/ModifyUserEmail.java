
package it.matrix.services.userprivacydata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for modifyUserEmail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="modifyUserEmail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="channel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="int_userId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ext_userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email_type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyUserEmail", propOrder = {
    "channel",
    "intUserId",
    "extUserId",
    "emailType",
    "email"
})
public class ModifyUserEmail {

    protected String channel;
    @XmlElement(name = "int_userId")
    protected int intUserId;
    @XmlElement(name = "ext_userId")
    protected String extUserId;
    @XmlElement(name = "email_type")
    protected String emailType;
    protected String email;

    /**
     * Gets the value of the channel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannel(String value) {
        this.channel = value;
    }

    /**
     * Gets the value of the intUserId property.
     * 
     */
    public int getIntUserId() {
        return intUserId;
    }

    /**
     * Sets the value of the intUserId property.
     * 
     */
    public void setIntUserId(int value) {
        this.intUserId = value;
    }

    /**
     * Gets the value of the extUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtUserId() {
        return extUserId;
    }

    /**
     * Sets the value of the extUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtUserId(String value) {
        this.extUserId = value;
    }

    /**
     * Gets the value of the emailType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailType() {
        return emailType;
    }

    /**
     * Sets the value of the emailType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailType(String value) {
        this.emailType = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

}
