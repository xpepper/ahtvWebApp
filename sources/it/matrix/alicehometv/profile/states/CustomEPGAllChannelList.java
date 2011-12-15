package it.matrix.alicehometv.profile.states;

import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.profile.EPG;

import java.sql.SQLException;
import java.util.*;

public class CustomEPGAllChannelList extends CustomEPGOperationJson
{

    @Override
    public List<Properties> getOutput(Map params)
    {
        int id = 0;
        try
        {
            String[] idTema = (String[]) params.get("themeID");
            id = Integer.parseInt(idTema[0]);
        }
        catch (Exception e)
        {
            id = 0;
        }

        EPG epg = new EPG(getConnection());
        List<Properties> list = null;
        try
        {
            list = epg.getChannelList(id);
            closeConnection();
        }
        catch (SQLException e)
        {
            ActivityLogger.logException(e);
        }
        return list;

    }
}
