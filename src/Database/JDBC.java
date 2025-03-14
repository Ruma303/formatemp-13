package Database;

import java.sql.*;

// Creiamo soltanto la connessione al DB
// I vari statements verranno definiti in una classe a parte, Statements appunto

public class JDBC {
	private static final String DB_SCHEMA = "jdbc";
	private static final String DB_DATABASE = "mysql";
	private static final String DB_HOST = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_NAME = "formatemp_scuoladb";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";

	private static final String DB_URL = DB_SCHEMA + ":" + DB_DATABASE + "://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME
			+ "?serverTimezone=UTC";

	// Metodo per ottenere la connessione
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	}
}