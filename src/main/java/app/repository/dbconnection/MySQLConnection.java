package app.repository.dbconnection;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;


@Component
public class MySQLConnection
{
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;


    static
    {
        config.setJdbcUrl( "jdbc:mysql://localhost:3306/myforum" );
        config.setUsername( "dev" );
        config.setPassword( "word" );
        dataSource = new HikariDataSource( config );
    }


    public MySQLConnection() {}


    public static Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }
}

