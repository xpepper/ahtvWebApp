
package it.matrix.services.userprivacydata;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for loadEmailFromIntID complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="loadEmailFromIntID">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="int_userid" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="channel_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email_type" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "loadEmailFromIntID", propOrder = {
    "intUserid",
    "channelId",
    "emailType"
})
public class LoadEmailFromIntID {

    @XmlElement(name = "int_userid")
    protected int intUserid;
    @XmlElement(name = "channel_id")
    protected String channelId;
    @XmlElement(name = "email_type", nillable = true)
    protected List<String> emailType;

    /**
     * Gets the value of the intUserid property.
     * 
     */
    public int getIntUserid() {
        return intUserid;
    }

    /**
     * Sets the value of the intUserid property.
     * 
     */
    public void setIntUserid(int value) {
        this.intUserid = value;
    }

    /**
     * Gets the value of the channelId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * Sets the value of the channelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelId(String value) {
        this.channelId = value;
    }

    /**
     * Gets the value of the emailType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the emailType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmailType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getEmailType() {
        if (emailType == null) {
            emailType = new ArrayList<String>();
        }
        return this.emailType;
    }

}
