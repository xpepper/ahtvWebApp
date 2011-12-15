package it.matrix.alicehometv.util;

import static org.apache.commons.lang.StringUtils.*;

import org.apache.commons.lang.StringUtils;

public class StringStorerBuilder
{
    public static StringStorer buildFromEncrypted(String anEncryptedKeyValueSequence)
    {
        String decryptedValue = EncryptionUtils.decrypt(anEncryptedKeyValueSequence);
        return buildFrom(decryptedValue);
    }
    
    public static StringStorer buildFrom(String aKeyValueSequence)
    {
        StringStorer stringStorer = new StringStorer();
        if (isNotEmpty(aKeyValueSequence))
        {            
            String[] splittedStrings = aKeyValueSequence.split("\\|");
            for (int i = 0; i < splittedStrings.length; i++)
            {
                String[] keyValuePair = splittedStrings[i].split("=");
                String key = keyValuePair[0];
                String value = valueFrom(keyValuePair);
                
                stringStorer.put(key, value);
            }
        }
        return stringStorer;
    }

    private static String valueFrom(String[] keyValuePair)
    {
        String value;
        if (keyValuePair.length == 2)
            value = keyValuePair[1];
        else 
            value = StringUtils.EMPTY;
        return value;
    }
}
