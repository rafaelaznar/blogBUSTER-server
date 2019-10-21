package net.ausiasmarch.connection.implementation;

import java.sql.Connection;
import java.sql.SQLException;
import net.ausiasmarch.connection.ConnectionInterface;
import net.ausiasmarch.setting.ConnectionSettings;
import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPConnectionImplementation implements ConnectionInterface {

    private Connection oConnection;
    private BasicDataSource config;

    @Override
    public Connection newConnection() throws SQLException {
        config = new BasicDataSource();
        config.setUrl(ConnectionSettings.getConnectionChain());
        config.setUsername(ConnectionSettings.databaseLogin);
        config.setPassword(ConnectionSettings.databasePassword);
        config.setMaxIdle(ConnectionSettings.getDatabaseMaxPoolSize);
        config.setMinIdle(ConnectionSettings.getDatabaseMinPoolSize);

        oConnection = config.getConnection();
        return oConnection;

    }

    @Override
    public void disposeConnection() throws SQLException {
        if (oConnection != null) {
            oConnection.close();
        }
        if (config != null) {
            config.close();
        }
    }

}
