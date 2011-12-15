package it.matrix.alicehometv.profile.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.db.DbConnectionProvider;
import it.matrix.alicehometv.db.util.AhtvDirectJDBCConnectionProviderFactory;
import it.matrix.alicehometv.profile.EPG;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

public class EPGTest
{
    DbConnectionProvider provider = AhtvDirectJDBCConnectionProviderFactory.createForDev();

    private EPG epg = new EPG(provider.connection());

    @Test
    public void testGetChannelList()
    {
        try
        {
            List<Properties> lista = epg.getChannelList(0);
            if (lista.size() < 1)
            {
                fail();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetThemeList()
    {
        List<Properties> lista;
        try
        {
            lista = epg.getThemeList();
            if (lista == null || lista.size() < 1)
            {
                fail();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void testGetUserChannelList()
    {
        List<Properties> lista;
        try
        {
            lista = epg.getUserChannelList("1");
            if (lista == null || lista.size() < 0)
            {
                fail();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            fail();
        }
    }
}
