package it.matrix.alicehometv.profile.states;

import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.profile.EPG;

import java.sql.SQLException;
import java.util.*;

public class CustomEPGGetUserChannels extends CustomEPGOperationJson
{

    @Override
    public List<Properties> getOutput(Map params)
    {

        EPG epg = new EPG(getConnection());
        try
        {
            return epg.getUserChannelList(userID);
        }
        catch (SQLException e)
        {
            ActivityLogger.logException(e);
        }
        finally
        {
            closeConnection();
        }
        return null;

    }
}
