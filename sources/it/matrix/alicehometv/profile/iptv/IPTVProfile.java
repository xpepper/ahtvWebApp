package it.matrix.alicehometv.profile.iptv;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class IPTVProfile
{

    private String purchasePin;
    private String pcPin;
    private Integer pcLevel;
    private String cli;

    public IPTVProfile()
    {
    }

    public IPTVProfile(String aCli, String aPurchasePin, String aParentalControlCode, Integer aParentalControlCodeLevel)
    {
        cli = aCli;
        purchasePin = aPurchasePin;
        pcPin = aParentalControlCode;
        pcLevel = aParentalControlCodeLevel;
    }

    public String getPurchasePin()
    {
        return purchasePin;
    }

    public void setPurchasePin(String purchasePin)
    {
        this.purchasePin = purchasePin;
    }

    public String getPcPin()
    {
        return pcPin;
    }

    public void setPcPin(String pcPin)
    {
        this.pcPin = pcPin;
    }

    public Integer getPcLevel()
    {
        return pcLevel;
    }

    public void setPcLevel(Integer pcLevel)
    {
        this.pcLevel = pcLevel;
    }

    public String cli()
    {
        return cli;
    }

    @Override
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
