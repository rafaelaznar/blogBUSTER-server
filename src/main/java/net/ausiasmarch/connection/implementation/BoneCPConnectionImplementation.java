package net.ausiasmarch.connection.implementation;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import net.ausiasmarch.connection.ConnectionInterface;
import net.ausiasmarch.setting.ConnectionSettings;

public class BoneCPConnectionImplementation implements ConnectionInterface {

    private Connection oConnection;
    private BoneCP oConnectionPool;

    @Override
    public Connection newConnection() throws SQLException {

        BoneCPConfig config = new BoneCPConfig();

        config.setJdbcUrl(ConnectionSettings.getConnectionChain()); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb

        config.setUsername(ConnectionSettings.databaseLogin);
        config.setPassword(ConnectionSettings.databasePassword);
        config.setMinConnectionsPerPartition(ConnectionSettings.getDatabaseMinPoolSize);
        config.setMaxConnectionsPerPartition(ConnectionSettings.getDatabaseMaxPoolSize);
        config.setPartitionCount(1);

        oConnectionPool = new BoneCP(config);
        oConnection = (Connection) oConnectionPool.getConnection();
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
