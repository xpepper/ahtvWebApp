package it.matrix.alicehometv;

import it.matrix.alicehometv.service.AhtvUserService;
import it.matrix.services.userprivacydata.Email;


public class EmailBuilder
{
    private static final String EMAIL_KEY = "EMAIL";

    public static Email ahtvEmailWith(String anAhtvEmail)
    {
        return EmailBuilder.buildWith(EmailUtils.EMAIL_AHTV_TYPE, anAhtvEmail);
    }

    public static Email originalEmailWith(String anOriginalEmail)
    {
        return EmailBuilder.buildWith(EmailUtils.EMAIL_SUNRISE_TYPE, anOriginalEmail);
    }

    private static Email buildWith(String emailType, String emailAddress)
    {
        Email email = new Email();
        email.setChannel(AhtvUserService.AHTV_CHANNEL_ID);
        email.setKey(EMAIL_KEY);
        email.setType(emailType);
        email.setValue(emailAddress);
        return email;
    }
}
