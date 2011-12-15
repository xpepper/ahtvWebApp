package it.matrix.services.userprivacydata;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

/**
 * <p>
 * Java class for emailResponse complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;emailResponse&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;email&quot; type=&quot;{http://services.backoffice.matrix.it/}email&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;response&quot; type=&quot;{http://services.backoffice.matrix.it/}resultResponse&quot; minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "emailResponse", propOrder = { "email", "response" })
public class EmailResponse
{

    @XmlElement(nillable = true)
    protected List<Email> email;
    protected ResultResponse response;

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
     * Gets the value of the response property.
     * 
     * @return possible object is {@link ResultResponse }
     */
    public ResultResponse getResponse()
    {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *            allowed object is {@link ResultResponse }
     */
    public void setResponse(ResultResponse value)
    {
        this.response = value;
    }

    public boolean isOk()
    {
        return getResponse().getCode() == 0;
    }

}
