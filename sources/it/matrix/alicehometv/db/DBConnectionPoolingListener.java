package it.matrix.alicehometv.db;

import it.matrix.alicehometv.logger.ActivityLogger;

import javax.naming.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

public class DBConnectionPoolingListener implements ServletContextListener
{
    public void contextInitialized(ServletContextEvent servletContextEvent)
    {
        try
        {
            Context context = (Context) new InitialContext().lookup("java:comp/env");
            DataSource dataSource = (DataSource) context.lookup("jdbc/ahtvDataSource");
            DbConnectionProvider dbConnectionProvider = new DbConnectionProviderImpl(dataSource);
            servletContextEvent.getServletContext().setAttribute(DbConnectionProvider.ATTRIBUTE_NAME, dbConnectionProvider);
        }
        catch (NamingException exception)
        {
            ActivityLogger.logException(exception);
        }
    }
    
    public void contextDestroyed(ServletContextEvent servletContextEvent)
    {
    }
}
