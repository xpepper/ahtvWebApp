
package it.telecomitalia.pssc;

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
 *         &lt;element name="PSSCOrderEntryRequestResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "psscOrderEntryRequestResult"
})
@XmlRootElement(name = "PSSCOrderEntryRequestResponse")
public class PSSCOrderEntryRequestResponse {

    @XmlElement(name = "PSSCOrderEntryRequestResult")
    protected String psscOrderEntryRequestResult;

    /**
     * Gets the value of the psscOrderEntryRequestResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPSSCOrderEntryRequestResult() {
        return psscOrderEntryRequestResult;
    }

    /**
     * Sets the value of the psscOrderEntryRequestResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPSSCOrderEntryRequestResult(String value) {
        this.psscOrderEntryRequestResult = value;
    }

}
