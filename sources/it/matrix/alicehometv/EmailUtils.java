package it.matrix.alicehometv;

import it.matrix.services.userprivacydata.Email;

import java.util.Arrays;
import java.util.List;

public class EmailUtils
{
    public static final String EMAIL_AHTV_TYPE = "email_ahtv";
    public static final String EMAIL_SUNRISE_TYPE = "email_sunrise";

//FIXME    public static final List<String> ALL_TYPES = Arrays.asList(EMAIL_SUNRISE_TYPE, EMAIL_AHTV_TYPE);
    public static final List<String> ALL_TYPES = Arrays.asList("", "");

    public static String detectAhtvEmailValueFrom(List<Email> emails)
    {
        return EmailUtils.detectEmailValueWithType(emails, EMAIL_AHTV_TYPE);
    }
    
    public static String detectOriginalEmailValueFrom(List<Email> emails)
    {
        return EmailUtils.detectEmailValueWithType(emails, EMAIL_SUNRISE_TYPE);
    }

    public static Email detectAhtvEmailFrom(List<Email> emails)
    {
        return detectEmailWithType(emails, EMAIL_AHTV_TYPE);
    }

    public static Email detectOriginalEmailFrom(List<Email> emails)
    {
        return detectEmailWithType(emails, EMAIL_SUNRISE_TYPE);
    }
    
    private static String detectEmailValueWithType(List<Email> emailList, String emailType)
    {
        Email detectedEmail = detectEmailWithType(emailList, emailType);
        return detectedEmail == null? null : detectedEmail.getValue();
    }

    private static Email detectEmailWithType(List<Email> emailList, String emailType)
    {
        for (Email email : emailList)
        {
            if (emailType.equals(email.getType()))
                return email;
        }
        
        return null;
    }
}
