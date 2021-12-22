package com.turbine.windscada.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Db {
    private static Logger logger = Logger.getLogger(Db.class.getName());
    private static HikariDataSource pool;

    static {
        try {

            HikariConfig hikariConfig114 = new HikariConfig();
            hikariConfig114.setDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");
            hikariConfig114.setJdbcUrl("jdbc:sqlserver://localhost:1433");
            hikariConfig114.addDataSourceProperty("user", "SA");
            hikariConfig114.addDataSourceProperty("password", "sS123456");
            hikariConfig114.addDataSourceProperty("databaseName", "WINDSCADA");
            hikariConfig114.setPoolName("windscada");
            hikariConfig114.setMinimumIdle(1);
            hikariConfig114.setMaximumPoolSize(20);
            hikariConfig114.setConnectionTestQuery("SELECT GETDATE();");
            pool = new HikariDataSource(hikariConfig114);
            logger.info("Pool created");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
        }
    }

    public static void close(AutoCloseable... objs) {
        for (AutoCloseable obj : objs)
            try {
                if (obj != null)
                    obj.close();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
            }
    }

    public static HikariDataSource getPds114() {
        return pool;
    }

    protected static Connection getConnection() throws SQLException {
        return pool.getConnection();
    }
}
