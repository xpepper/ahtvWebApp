package it.matrix.alicehometv.db.test;

import static junit.framework.Assert.*;
import it.matrix.alicehometv.db.DbConnectionProviderImpl;
import it.matrix.alicehometv.logger.ActivityLogger;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestDbConnectionProvider
{
    private Mockery context = new JUnit4Mockery();
    private DataSource mockDataSource = context.mock(DataSource.class);

    @Test
    public void shouldReturnNullWhenGettingConnectionFails() throws Exception
    {
        ActivityLogger.off();
        context.checking(new Expectations()
        {{
            ignoring(mockDataSource).setLogWriter(with(any(PrintWriter.class)));
            one(mockDataSource).getConnection();
            will(throwException(new SQLException("Generated for test")));
        }});

        Connection connection = new DbConnectionProviderImpl(mockDataSource).connection();
        ActivityLogger.resume();

        assertNull(connection);
    }
}
