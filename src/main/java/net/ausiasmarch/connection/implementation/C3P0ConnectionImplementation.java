package net.ausiasmarch.connection.implementation;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import net.ausiasmarch.connection.ConnectionInterface;
import net.ausiasmarch.setting.ConnectionSettings;

public class C3P0ConnectionImplementation implements ConnectionInterface {

    private Connection oConnection;
    private ComboPooledDataSource config;

    @Override
    public Connection newConnection() throws SQLException {
        config = new ComboPooledDataSource();

        config.setJdbcUrl(ConnectionSettings.getConnectionChain());
        config.setUser(ConnectionSettings.databaseLogin);
        config.setPassword(ConnectionSettings.databasePassword);
        config.setMaxPoolSize(ConnectionSettings.getDatabaseMaxPoolSize);
        config.setMinPoolSize(ConnectionSettings.getDatabaseMinPoolSize);

        config.setInitialPoolSize(5);

        config.setAcquireIncrement(5);

        config.setMaxStatements(100);

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
