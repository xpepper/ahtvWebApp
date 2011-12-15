package it.matrix.alicehometv.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.EmailBuilder;
import it.matrix.alicehometv.EmailUtils;
import it.matrix.services.userprivacydata.Email;

import java.util.*;

import org.junit.Test;

public class TestEmailUtils
{
    @Test
    public void testDetectEmailByType() throws Exception
    {
        List<Email> emptyList = Collections.emptyList();
        assertNull(EmailUtils.detectAhtvEmailFrom(emptyList));
        assertNull(EmailUtils.detectOriginalEmailFrom(emptyList));

        Email ahtvEmail = EmailBuilder.ahtvEmailWith("anAhtvEmail");
        Email originalEmail = EmailBuilder.originalEmailWith("anOriginalEmail");
        List<Email> emails = Arrays.asList(ahtvEmail, originalEmail);

        assertEquals(ahtvEmail, EmailUtils.detectAhtvEmailFrom(emails));
        assertEquals(originalEmail, EmailUtils.detectOriginalEmailFrom(emails));

        assertEquals(ahtvEmail.getValue(), EmailUtils.detectAhtvEmailValueFrom(emails));
        assertEquals(originalEmail.getValue(), EmailUtils.detectOriginalEmailValueFrom(emails));

    }
}
