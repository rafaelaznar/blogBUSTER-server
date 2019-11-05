package net.ausiasmarch.connection.implementation;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.ausiasmarch.connection.ConnectionInterface;
import net.ausiasmarch.setting.ConnectionSettings;

public class HikariConnectionImplementation implements ConnectionInterface {

    private Connection oConnection;
    private HikariDataSource oConnectionPool;

    @Override
    public Connection newConnection() throws SQLException {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(ConnectionSettings.getConnectionChain());
        config.setUsername(ConnectionSettings.databaseLogin);
        config.setPassword(ConnectionSettings.databasePassword);
        config.setMaximumPoolSize(ConnectionSettings.getDatabaseMaxPoolSize);
        config.setMinimumIdle(ConnectionSettings.getDatabaseMinPoolSize);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setLeakDetectionThreshold(15000);
        config.setConnectionTestQuery("SELECT 1");
        config.setConnectionTimeout(2000);

        oConnectionPool = new HikariDataSource(config);
        oConnection = oConnectionPool.getConnection();
        return oConnection;

    }

    @Override
    public void disposeConnection() throws SQLException {
        if (oConnection != null) {
            oConnection.close();
        }
        if (oConnectionPool != null) {
            oConnectionPool.close();
        }
    }

}
