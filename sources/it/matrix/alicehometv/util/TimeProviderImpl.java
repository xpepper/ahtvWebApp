package it.matrix.alicehometv.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeProviderImpl implements TimeProvider
{
    @Override
    public Calendar now()
    {
        return GregorianCalendar.getInstance();
    }

}
