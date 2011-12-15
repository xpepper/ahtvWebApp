package it.matrix.alicehometv.db;

import java.sql.Connection;

public interface DbConnectionProvider
{
    static final String ATTRIBUTE_NAME = "dbConnectionProvider";
    
    Connection connection();
    void close(Connection aConnection);
}