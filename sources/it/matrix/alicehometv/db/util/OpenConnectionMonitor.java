package it.matrix.alicehometv.db.util;

import it.matrix.alicehometv.db.DbConnectionProvider;

import java.sql.*;
import java.util.Date;

public class OpenConnectionMonitor
{
    private static final String HOSTNAME = "dev-aiuto-master";

    public static void main(String... args)
    {
        DbConnectionProvider connectionProvider = AhtvDirectJDBCConnectionProviderFactory.createForCollaudo();
        Connection connection = connectionProvider.connection();
        try
        {
            Statement statement = connection.createStatement();
            while (true)
            {
                System.out.println("-- " + new Date() + " --");
                ResultSet result = statement.executeQuery("select osuser, machine, count(*) from V$SESSION group by osuser, machine, status");
                while (result.next())
                    System.out.println("user " + result.getObject(1) + "@" + result.getObject(2) + " has #conn:" + result.getObject(3));

                Thread.sleep(2000);
            }
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            connectionProvider.close(connection);
        }
    }
}
