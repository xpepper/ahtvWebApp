package it.matrix.alicehometv.db.util;

import it.matrix.alicehometv.db.DbConnectionProvider;
import it.matrix.alicehometv.logger.ActivityLogger;

import java.sql.Connection;
import java.sql.DriverManager;

public class DirectJDBCConnectionProvider implements DbConnectionProvider
{
    private final String itsJdbcUrl;
    private final String itsUsername;
    private final String itsPassword;

    public DirectJDBCConnectionProvider(String jdbcUrl, String username, String password)
    {
        itsJdbcUrl = jdbcUrl;
        itsUsername = username;
        itsPassword = password;
    }

    public Connection connection()
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(itsJdbcUrl, itsUsername, itsPassword);
        }
        catch (Exception e)
        {
            ActivityLogger.logException(e);
            return null;
        }
    }

    public void close(Connection connection)
    {
        if (connection != null)
        {
            try
            {
                connection.close();
            }
            catch (Exception e)
            {
                ActivityLogger.logException(e);
            }
        }
    }
}
