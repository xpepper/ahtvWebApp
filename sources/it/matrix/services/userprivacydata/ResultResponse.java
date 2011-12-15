package it.matrix.services.userprivacydata;

import javax.xml.bind.annotation.*;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Java class for resultResponse complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;resultResponse&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;code&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}int&quot;/&gt;
 *         &lt;element name=&quot;message&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultResponse", propOrder = { "code", "message" })
public class ResultResponse
{

    protected int code;
    protected String message;

    /**
     * Gets the value of the code property.
     */
    public int getCode()
    {
        return code;
    }

    /**
     * Sets the value of the code property.
     */
    public void setCode(int value)
    {
        this.code = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return possible object is {@link String }
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *            allowed object is {@link String }
     */
    public void setMessage(String value)
    {
        this.message = value;
    }
    
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

}
