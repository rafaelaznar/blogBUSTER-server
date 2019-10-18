package net.ausiasmarch.connection.implementation;

import java.sql.Connection;
import java.sql.SQLException;
import net.ausiasmarch.connection.ConnectionInterface;
import net.ausiasmarch.setting.ConnectionSettings;
import org.vibur.dbcp.ViburDBCPDataSource;

public class ViburConnectionImplementation implements ConnectionInterface {

    private Connection oConnection;
    private ViburDBCPDataSource oConnectionPool;

    @Override
    public Connection newConnection() throws SQLException {

        oConnectionPool = new ViburDBCPDataSource();
        oConnectionPool.setJdbcUrl(ConnectionSettings.getConnectionChain());
        oConnectionPool.setUsername(ConnectionSettings.databaseLogin);
        oConnectionPool.setPassword(ConnectionSettings.databasePassword);
        oConnectionPool.setPoolMaxSize(ConnectionSettings.getDatabaseMaxPoolSize);
        oConnectionPool.setPoolInitialSize(ConnectionSettings.getDatabaseMinPoolSize);

        oConnectionPool.setConnectionIdleLimitInSeconds(30);
        oConnectionPool.setTestConnectionQuery("isValid");

        oConnectionPool.setLogQueryExecutionLongerThanMs(500);
        oConnectionPool.setLogStackTraceForLongQueryExecution(true);

        oConnectionPool.setStatementCacheMaxSize(200);

        oConnectionPool.start();

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
