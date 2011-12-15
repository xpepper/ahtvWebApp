
package it.matrix.services.user;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userPersonalData" type="{http://types.detail.ws.rossoalice.telecomitalia.it}UserPersonalData" minOccurs="0"/>
 *         &lt;element name="userProfileData" type="{http://types.detail.ws.rossoalice.telecomitalia.it}UserProfileData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserDetail", propOrder = {
    "userPersonalData",
    "userProfileData"
})
public class UserDetail {

    @XmlElementRef(name = "userPersonalData", namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", type = JAXBElement.class)
    protected JAXBElement<UserPersonalData> userPersonalData;
    @XmlElementRef(name = "userProfileData", namespace = "http://types.detail.ws.rossoalice.telecomitalia.it", type = JAXBElement.class)
    protected JAXBElement<UserProfileData> userProfileData;

    /**
     * Gets the value of the userPersonalData property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link UserPersonalData }{@code >}
     *     
     */
    public JAXBElement<UserPersonalData> getUserPersonalData() {
        return userPersonalData;
    }

    /**
     * Sets the value of the userPersonalData property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link UserPersonalData }{@code >}
     *     
     */
    public void setUserPersonalData(JAXBElement<UserPersonalData> value) {
        this.userPersonalData = ((JAXBElement<UserPersonalData> ) value);
    }

    /**
     * Gets the value of the userProfileData property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link UserProfileData }{@code >}
     *     
     */
    public JAXBElement<UserProfileData> getUserProfileData() {
        return userProfileData;
    }

    /**
     * Sets the value of the userProfileData property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link UserProfileData }{@code >}
     *     
     */
    public void setUserProfileData(JAXBElement<UserProfileData> value) {
        this.userProfileData = ((JAXBElement<UserProfileData> ) value);
    }

}
