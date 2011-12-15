package it.matrix.alicehometv;

import it.matrix.services.userprivacydata.Phone;

import java.util.Arrays;
import java.util.List;

public class PhoneUtils
{
    public static final String MOBILE_AHTV_TYPE = "mobile_ahtv";
    public static final String MOBILE_SUNRISE_TYPE = "mobile_sunrise";
    
//FIXME    public static final List<String> ALL_TYPES = Arrays.asList(MOBILE_SUNRISE_TYPE, MOBILE_AHTV_TYPE);
    public static final List<String> ALL_TYPES = Arrays.asList("", "");

    public static Phone detectAhtvPhoneFrom(List<Phone> phoneList)
    {
        return detectMobileWithType(phoneList, MOBILE_AHTV_TYPE);
    }

    public static Phone detectOriginalPhoneFrom(List<Phone> phoneList)
    {
        return detectMobileWithType(phoneList, MOBILE_SUNRISE_TYPE);
    }

    public static String detectAhtvPhoneValueFrom(List<Phone> phoneList)
    {
        return detectMobileValueWithType(phoneList, MOBILE_AHTV_TYPE);
    }

    public static String detectOriginalPhoneValueFrom(List<Phone> phoneList)
    {
        return detectMobileValueWithType(phoneList, MOBILE_SUNRISE_TYPE);
    }
    
    private static Phone detectMobileWithType(List<Phone> phoneList, String mobileType)
    {
        for (Phone phone : phoneList)
        {
            if (mobileType.equals(phone.getType()))
                return phone;
        }
        return null;
    }
    
    private static String detectMobileValueWithType(List<Phone> phoneList, String mobileType)
    {
        Phone detectedMobilePhone = detectMobileWithType(phoneList, mobileType);
        return detectedMobilePhone == null? null : detectedMobilePhone.getValue();

    }
}
