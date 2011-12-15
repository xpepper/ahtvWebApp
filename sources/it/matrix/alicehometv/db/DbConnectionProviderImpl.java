package it.matrix.alicehometv.db;

import it.matrix.alicehometv.logger.ActivityLogger;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class DbConnectionProviderImpl implements DbConnectionProvider
{
    private final DataSource itsDataSource;

    public DbConnectionProviderImpl(DataSource aDataSource)
    {
        itsDataSource = aDataSource;
        configureDataSourceLogWriter();
    }

    public Connection connection()
    {
        logPoolStatus();
        ActivityLogger.debug("Getting a connection from the pool");
        try
        {
            return itsDataSource.getConnection();
        }
        catch (SQLException ex)
        {
            ActivityLogger.error("Cannot get a connection");
            ActivityLogger.logException(ex);
            return null;
        }
    }

    public void close(Connection connection)
    {
        logPoolStatus();
        ActivityLogger.debug("Returning a connection to the pool");
        try
        {
            synchronized (this)
            {
                if (connection != null && !connection.isClosed())
                    connection.close();
            }
        }
        catch (SQLException ex)
        {
            ActivityLogger.error("Cannot close connection");
            ActivityLogger.logException(ex);
        }
    }

    private void configureDataSourceLogWriter()
    {
        try
        {
            itsDataSource.setLogWriter(new PrintWriter(System.out));
        }
        catch (Exception ex)
        {
            ActivityLogger.logException(ex);
        }
    }

    private void logPoolStatus()
    {
        if (itsDataSource.getClass().isAssignableFrom(BasicDataSource.class))
        {
            BasicDataSource datasource = (BasicDataSource) itsDataSource;
            ActivityLogger.debug("Connection Pool Status: active connections=" + datasource.getNumActive() + ", idle connections=" + datasource.getNumIdle());
        }
    }
}
