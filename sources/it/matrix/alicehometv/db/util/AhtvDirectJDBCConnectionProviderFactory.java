package it.matrix.alicehometv.db.util;

public class AhtvDirectJDBCConnectionProviderFactory
{
    public static DirectJDBCConnectionProvider createForDev()
    {
        return new DirectJDBCConnectionProvider("jdbc:oracle:thin:@dev-db-oracle10.bko.vtin.net:1521:dbdev10", "db_ahtv", "db_ahtv");
    }

    public static DirectJDBCConnectionProvider createForCollaudo()
    {
        return new DirectJDBCConnectionProvider("jdbc:oracle:thin:@db-coll-ahtv.virgilio.net:1521:DBCOLLAHTV", "db_webtv", "db_webtv");
    }
}
