package it.matrix.services.userprivacydata;

import javax.xml.bind.annotation.*;
import org.apache.commons.lang.builder.*;

/**
 * <p>
 * Java class for baseType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;baseType&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;channel&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;key&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;type&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;value&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseType", propOrder = { "channel", "key", "type", "value" })
public class BaseType
{

    protected String channel;
    protected String key;
    protected String type;
    protected String value;

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
     * Gets the value of the key property.
     * 
     * @return possible object is {@link String }
     */
    public String getKey()
    {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *            allowed object is {@link String }
     */
    public void setKey(String value)
    {
        this.key = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return possible object is {@link String }
     */
    public String getType()
    {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *            allowed object is {@link String }
     */
    public void setType(String value)
    {
        this.type = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return possible object is {@link String }
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *            allowed object is {@link String }
     */
    public void setValue(String value)
    {
        this.value = value;
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
