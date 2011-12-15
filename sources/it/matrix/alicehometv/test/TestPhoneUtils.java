package it.matrix.alicehometv.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.PhoneBuilder;
import it.matrix.alicehometv.PhoneUtils;
import it.matrix.services.userprivacydata.Phone;

import java.util.*;

import org.junit.Test;


public class TestPhoneUtils
{
    @Test
    public void testDetectPhoneByType() throws Exception
    {
        List<Phone> emptyList = Collections.emptyList();
        assertNull(PhoneUtils.detectAhtvPhoneFrom(emptyList));
        assertNull(PhoneUtils.detectOriginalPhoneFrom(emptyList));
        
        Phone ahtvPhone = PhoneBuilder.ahtvPhoneWith("anAhtvPhone");
        Phone originalPhone = PhoneBuilder.originalPhoneWith("anOriginalPhone");
        List<Phone> phones = Arrays.asList(ahtvPhone, originalPhone);

        assertEquals(ahtvPhone, PhoneUtils.detectAhtvPhoneFrom(phones));
        assertEquals(originalPhone, PhoneUtils.detectOriginalPhoneFrom(phones));
        
        assertEquals(ahtvPhone.getValue(), PhoneUtils.detectAhtvPhoneValueFrom(phones));
        assertEquals(originalPhone.getValue(), PhoneUtils.detectOriginalPhoneValueFrom(phones));

    }
}
