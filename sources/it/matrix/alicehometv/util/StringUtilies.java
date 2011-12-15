package it.matrix.alicehometv.util;

import static org.apache.commons.lang.StringUtils.*;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class StringUtilies
{
    public static boolean notEquals(String aString, String anotherString)
    {
        return !StringUtils.equals(aString, anotherString);
    }

    public static String firstValueFrom(String[] values)
    {
        return ArrayUtils.isEmpty(values) ? null : values[0];
    }

    public static String nullSafeString(String aString)
    {
        return aString == null ? EMPTY : aString;
    }

}
