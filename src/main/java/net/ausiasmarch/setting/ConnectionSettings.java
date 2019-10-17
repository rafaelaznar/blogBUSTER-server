package net.ausiasmarch.setting;



public class ConnectionSettings {

	public static enum EnumConstans  {
		Hikari,
		DBCP,
		BoneCP,
		C3P0,
		Vibur
	};

	public static final EnumConstans connectionPool = EnumConstans.Hikari;
	public static final String databaseName = "blogbuster";
	public static final String databaseLogin = "blogbuster";
	public static final String databasePassword = "bitnami";
	public static final String databasePort = "3306";
	public static final String databaseHost = "localhost";
	public static final int getDatabaseMaxPoolSize = 10;
	public static final int getDatabaseMinPoolSize = 5;

	public static String getConnectionChain() {
		return "jdbc:mysql://" + ConnectionSettings.databaseHost + ":" + ConnectionSettings.databasePort + "/"
				+ ConnectionSettings.databaseName;
	}

}
