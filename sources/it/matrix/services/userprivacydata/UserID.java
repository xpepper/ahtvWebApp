package it.matrix.services.userprivacydata;

import javax.xml.bind.annotation.*;

import org.apache.commons.lang.builder.*;

/**
 * <p>
 * Java class for userID complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;userID&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;extUserId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;intUserId&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}int&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userID", propOrder = { "extUserId", "intUserId" })
public class UserID
{

    protected String extUserId;
    protected int intUserId;

    /**
     * Gets the value of the extUserId property.
     * 
     * @return possible object is {@link String }
     */
    public String getExtUserId()
    {
        return extUserId;
    }

    /**
     * Sets the value of the extUserId property.
     * 
     * @param value
     *            allowed object is {@link String }
     */
    public void setExtUserId(String value)
    {
        this.extUserId = value;
    }

    /**
     * Gets the value of the intUserId property.
     */
    public int getIntUserId()
    {
        return intUserId;
    }

    /**
     * Sets the value of the intUserId property.
     */
    public void setIntUserId(int value)
    {
        this.intUserId = value;
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
}
