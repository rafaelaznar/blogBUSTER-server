
package net.ausiasmarch.factory;

import net.ausiasmarch.connection.ConnectionInterface;
import net.ausiasmarch.connection.implementation.BoneCPConnectionImplementation;
import net.ausiasmarch.connection.implementation.C3P0ConnectionImplementation;
import net.ausiasmarch.connection.implementation.DBCPConnectionImplementation;
import net.ausiasmarch.connection.implementation.HikariConnectionImplementation;
import net.ausiasmarch.connection.implementation.ViburConnectionImplementation;
import net.ausiasmarch.setting.ConnectionSettings;


public class ConnectionFactory {

    public static ConnectionInterface getConnection(ConnectionSettings.EnumConstans enumConnection) {
        ConnectionInterface oConnectionInterface;
        switch (enumConnection) {
            case Hikari:
                oConnectionInterface = new HikariConnectionImplementation();
                break;
            case DBCP:
                oConnectionInterface = new DBCPConnectionImplementation();
                break;
            case BoneCP:
                oConnectionInterface = new BoneCPConnectionImplementation();
                break;
            case C3P0:
                oConnectionInterface = new C3P0ConnectionImplementation();
                break;
            case Vibur:
                oConnectionInterface = new ViburConnectionImplementation();
                break;
            default:
                oConnectionInterface = new HikariConnectionImplementation();
                break;
        }
        return oConnectionInterface;

    }
}
