package it.matrix.alicehometv.profile.states;

import it.matrix.alicehometv.db.DbConnectionProvider;
import it.matrix.alicehometv.logger.ActivityLogger;
import it.matrix.alicehometv.util.AhtvCookie;
import it.matrix.alicehometv.util.CookieValueDetector;
import it.telecomitalia.rossoalice.aa.filters.constants.Constants;
import it.telecomitalia.rossoalice.aa.sandbox.constants.Parameters;

import java.sql.Connection;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class CustomEPGOperation
{
    private static Map<String, String> associationMap = new TreeMap<String, String>();

    // TODO: Parametrizzare
    static
    {
        associationMap.put("/allChannelList", "it.matrix.alicehometv.profile.states.CustomEPGAllChannelList");
        associationMap.put("/themeList", "it.matrix.alicehometv.profile.states.CustomEPGThemeList");
        associationMap.put("/getUserChannelList", "it.matrix.alicehometv.profile.states.CustomEPGGetUserChannels");
        associationMap.put("/setUserChannelList", "it.matrix.alicehometv.profile.states.CustomEPGSetUserChannels");
    }

    private DbConnectionProvider dbConnectionProvider;

    public DbConnectionProvider getDbConnectionProvider()
    {
        return dbConnectionProvider;
    }

    public void setDbConnectionProvider(DbConnectionProvider dbConnectionProvider)
    {
        this.dbConnectionProvider = dbConnectionProvider;
    }

    Connection conn = null;

    public Connection getConnection()
    {
        conn = this.dbConnectionProvider.connection();
        return conn;
    }

    public void closeConnection()
    {
        dbConnectionProvider.close(conn);
    }

    public static CustomEPGOperation getAssociatedState(String key)
    {
        if (key == null || key.length() < 1)
        {
            return null;
        }
        String opClassName = associationMap.get(key);
        if (opClassName != null)
        {
            try
            {
                CustomEPGOperation op = (CustomEPGOperation) Class.forName(opClassName).newInstance();
                return op;
            }
            catch (Exception e)
            {
                ActivityLogger.logException(e);
            }
        }

        String regex = "/\\d{4}?-\\d{2}?-\\d{2}?/(.)*?\\.xml(\\?(.)*?)??";
        if (key != null && key.matches(regex))
        {
            String className = "it.matrix.alicehometv.profile.states.CustomEPGUserChannelList";
            try
            {
                return (CustomEPGOperation) Class.forName(className).newInstance();
            }
            catch (Exception e)
            {
                ActivityLogger.logException(e);
            }

        }
        return null;
    }

    protected String getUserID(HttpServletRequest req)
    {
        AhtvCookie ahtvCookie = new CookieValueDetector(req.getCookies()).detectAhtvCookie();
        String id = ahtvCookie.get("ahtvId");

        String sunrisePassportCode = (String) req.getAttribute(Parameters.AASANDBOX_SPC);
        if (sunrisePassportCode == null || sunrisePassportCode.length() < 1)
        {
            sunrisePassportCode = (String) req.getAttribute(Constants.AUTHEN_USER_SPC);
            if (sunrisePassportCode == null || sunrisePassportCode.length() < 1)
            {
                ActivityLogger.debug("PassportCode vuoto");
                // L'id esiste ma non e' loggato: non consideriamo l'id come
                // valido
                id = null;
            }
        }

        if (id == null)
        {
            id = "";
        }
        return id;
    }

    public abstract void doOperation(HttpServletRequest req, HttpServletResponse resp);
}
