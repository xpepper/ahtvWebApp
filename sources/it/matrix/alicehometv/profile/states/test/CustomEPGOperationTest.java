package it.matrix.alicehometv.profile.states.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.db.DbConnectionProvider;
import it.matrix.alicehometv.db.util.AhtvDirectJDBCConnectionProviderFactory;
import it.matrix.alicehometv.profile.states.CustomEPGOperation;
import it.matrix.alicehometv.profile.states.CustomEPGOperationJson;

import java.util.*;

import org.junit.Test;

public class CustomEPGOperationTest
{

    @Test
    public void testGetAssociatedState()
    {
        /*
         * associationMap.put("/allChannelList", associationMap.put("/themeList", ""); associationMap.put("/getUserChannelList", ""); associationMap.put("/setUserChannelList", "");
         */
        CustomEPGOperation res = CustomEPGOperation.getAssociatedState("/allChannelList");
        assertNotNull(res);
        assertSame("it.matrix.alicehometv.profile.states.CustomEPGAllChannelList", res.getClass().getName());

        res = CustomEPGOperation.getAssociatedState("/themeList");
        assertNotNull(res);
        assertSame("it.matrix.alicehometv.profile.states.CustomEPGThemeList", res.getClass().getName());

        res = CustomEPGOperation.getAssociatedState("/getUserChannelList");
        assertNotNull(res);
        assertSame("it.matrix.alicehometv.profile.states.CustomEPGGetUserChannels", res.getClass().getName());

        res = CustomEPGOperation.getAssociatedState("/setUserChannelList");
        assertNotNull(res);
        assertSame("it.matrix.alicehometv.profile.states.CustomEPGSetUserChannels", res.getClass().getName());

    }

    @Test
    public void testGetOutput()
    {

        DbConnectionProvider provider = AhtvDirectJDBCConnectionProviderFactory.createForDev();

        // allChannelList
        CustomEPGOperationJson res = (CustomEPGOperationJson) CustomEPGOperation.getAssociatedState("/allChannelList");
        assertNotNull(res);
        res.setDbConnectionProvider(provider);
        Map<String, String[]> map = new TreeMap<String, String[]>();
        map.put("themeID", new String[] { "1" });
        List<Properties> list = res.getOutput(map);
        assertNotNull(list);
        String json = CustomEPGOperationJson.propertiesList2JSON(list);
        assertNotNull(json);

        // themeList
        res = (CustomEPGOperationJson) CustomEPGOperation.getAssociatedState("/themeList");
        assertNotNull(res);
        res.setDbConnectionProvider(provider);
        list = res.getOutput(map);
        assertNotNull(list);
        json = CustomEPGOperationJson.propertiesList2JSON(list);
        assertNotNull(json);

        // setUserChannelList
        res = (CustomEPGOperationJson) CustomEPGOperation.getAssociatedState("/setUserChannelList");
        assertNotNull(res);
        res.setDbConnectionProvider(provider);
        map = new TreeMap<String, String[]>();
        map.put("channel", new String[] { "RAIUNO", "RAIDUE", "RAITRE" });
        list = res.getOutput(map);
        assertNotNull(list);
        json = CustomEPGOperationJson.propertiesList2JSON(list);
        assertNotNull(json);

    }

}
