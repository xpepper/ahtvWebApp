
package it.matrix.services.userprofiledata;

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
 *         &lt;element name="apc" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "apc"
})
@XmlRootElement(name = "getSpcFromApc")
public class GetSpcFromApc {

    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long apc;

    /**
     * Gets the value of the apc property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getApc() {
        return apc;
    }

    /**
     * Sets the value of the apc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setApc(Long value) {
        this.apc = value;
    }

}
