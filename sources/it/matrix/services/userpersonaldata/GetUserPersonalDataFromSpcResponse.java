
package it.matrix.services.userpersonaldata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="out" type="{http://types.detail.ws.rossoalice.telecomitalia.it}UserPersonalData"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "out"
})
@XmlRootElement(name = "getUserPersonalDataFromSpcResponse", namespace = "urn:aa.rossoalice.alice.it")
public class GetUserPersonalDataFromSpcResponse {

    @XmlElement(namespace = "urn:aa.rossoalice.alice.it", required = true, nillable = true)
    protected UserPersonalData out;

    /**
     * Gets the value of the out property.
     * 
     * @return
     *     possible object is
     *     {@link UserPersonalData }
     *     
     */
    public UserPersonalData getOut() {
        return out;
    }

    /**
     * Sets the value of the out property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserPersonalData }
     *     
     */
    public void setOut(UserPersonalData value) {
        this.out = value;
    }

}
