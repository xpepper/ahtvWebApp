package it.matrix.alicehometv;

import it.matrix.alicehometv.service.AhtvUserService;
import it.matrix.services.userprivacydata.Phone;


public class PhoneBuilder
{
    private static final String PHONE_KEY = "PHONE";

    public static Phone originalPhoneWith(String anOriginalMobileNumber)
    {
        return phoneWith(PhoneUtils.MOBILE_SUNRISE_TYPE, anOriginalMobileNumber);
    }

    public static Phone ahtvPhoneWith(String anAhtvMobileNumber)
    {
        return phoneWith(PhoneUtils.MOBILE_AHTV_TYPE, anAhtvMobileNumber);
    }

    private static Phone phoneWith(String phoneType, String phoneNumber)
    {
        Phone phobeNumber = new Phone();
        phobeNumber.setChannel(AhtvUserService.AHTV_CHANNEL_ID);
        phobeNumber.setKey(PHONE_KEY);
        phobeNumber.setType(phoneType);
        phobeNumber.setValue(phoneNumber);
        return phobeNumber;
    }
}
