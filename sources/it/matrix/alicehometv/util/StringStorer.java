package it.matrix.alicehometv.util;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class StringStorer
{
    private HashMap<String, String> itsStore;

    public StringStorer()
    {
        itsStore = new HashMap<String, String>();
    }

    public String get(String aKey)
    {
        return itsStore.get(aKey);
    }

    public StringStorer put(String aKey, String aValue)
    {   
        if (aValue != null) 
            itsStore.put(aKey, aValue);
        return this;
    }
    
    public String asEncryptedString()
    {
        return EncryptionUtils.encrypt(asString());
    }
    
    private String asString()
    {
        StringBuffer stringBuffer = new StringBuffer();
        for (String eachKey : itsStore.keySet())
            stringBuffer.append(eachKey + "=" + itsStore.get(eachKey) + "|");

        return ((stringBuffer.length() > 0)) ? removeLastCharFrom(stringBuffer) : StringUtils.EMPTY;
    }

    private String removeLastCharFrom(StringBuffer stringBuffer)
    {
        return stringBuffer.substring(0, stringBuffer.length() - 1);
    }

    public boolean equals(Object otherObject)
    {
        return EqualsBuilder.reflectionEquals(this, otherObject);
    }

    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public String toString()
    {
        return asString();
    }
}
