package it.matrix.services.userprivacydata;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

import org.apache.commons.lang.builder.*;

/**
 * <p>
 * Java class for userPDP complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;userPDP&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;address&quot; type=&quot;{http://services.backoffice.matrix.it/}address&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;anag&quot; type=&quot;{http://services.backoffice.matrix.it/}userAnag&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;channel&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;email&quot; type=&quot;{http://services.backoffice.matrix.it/}email&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;phone&quot; type=&quot;{http://services.backoffice.matrix.it/}phone&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;property&quot; type=&quot;{http://services.backoffice.matrix.it/}property&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;userId&quot; type=&quot;{http://services.backoffice.matrix.it/}userID&quot; minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userPDP", propOrder = { "address", "anag", "channel", "email", "phone", "property", "userId" })
public class UserPDP
{

    @XmlElement(nillable = true)
    protected List<Address> address;
    @XmlElement(nillable = true)
    protected List<UserAnag> anag;
    protected String channel;
    @XmlElement(nillable = true)
    protected List<Email> email;
    @XmlElement(nillable = true)
    protected List<Phone> phone;
    @XmlElement(nillable = true)
    protected List<Property> property;
    protected UserID userId;

    /**
     * Gets the value of the address property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the address
     * property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getAddress().add(newItem);
     * </pre>
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Address }
     */
    public List<Address> getAddress()
    {
        if (address == null)
        {
            address = new ArrayList<Address>();
        }
        return this.address;
    }

    /**
     * Gets the value of the anag property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the anag
     * property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getAnag().add(newItem);
     * </pre>
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link UserAnag }
     */
    public List<UserAnag> getAnag()
    {
        if (anag == null)
        {
            anag = new ArrayList<UserAnag>();
        }
        return this.anag;
    }

    /**
     * Gets the value of the channel property.
     * 
     * @return possible object is {@link String }
     */
    public String getChannel()
    {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *            allowed object is {@link String }
     */
    public void setChannel(String value)
    {
        this.channel = value;
    }

    /**
     * Gets the value of the email property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the email
     * property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getEmail().add(newItem);
     * </pre>
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Email }
     */
    public List<Email> getEmail()
    {
        if (email == null)
        {
            email = new ArrayList<Email>();
        }
        return this.email;
    }

    /**
     * Gets the value of the phone property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the phone
     * property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getPhone().add(newItem);
     * </pre>
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Phone }
     */
    public List<Phone> getPhone()
    {
        if (phone == null)
        {
            phone = new ArrayList<Phone>();
        }
        return this.phone;
    }

    /**
     * Gets the value of the property property.
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the property
     * property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getProperty().add(newItem);
     * </pre>
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Property }
     */
    public List<Property> getProperty()
    {
        if (property == null)
        {
            property = new ArrayList<Property>();
        }
        return this.property;
    }

    public boolean equals(Object aObj)
    {
        return EqualsBuilder.reflectionEquals(this, aObj);
    }

    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return possible object is {@link UserID }
     */
    public UserID getUserId()
    {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *            allowed object is {@link UserID }
     */
    public void setUserId(UserID value)
    {
        this.userId = value;
    }

}
