package it.matrix.alicehometv.profile.states;

import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.profile.EPG;

import java.sql.SQLException;
import java.util.*;

public class CustomEPGSetUserChannels extends CustomEPGOperationJson
{

    @Override
    public List<Properties> getOutput(Map params)
    {

        EPG epg = new EPG(getConnection());
        List<String> inList = new LinkedList<String>();
        String[] listaCanali = (String[]) params.get("channel");
        if (listaCanali != null)
        {
            List<String> tmpList = Arrays.asList(listaCanali);
            for (String s : tmpList)
            {
                if (s != null && s.length() > 0)
                {
                    inList.add(s);
                }
            }
        }
        List<Properties> list = new Vector<Properties>();
        Properties p = new Properties();
        try
        {
            if (epg.setUserChannelList(userID, inList))
            {
                p.put("result", "OK");
            }
            else
            {
                p.put("result", "ERROR");
            }
            list.add(p);
        }
        catch (SQLException e)
        {
            ActivityLogger.logException(e);
        }
        closeConnection();
        return list;
    }

}
