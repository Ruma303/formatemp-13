package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Esempio classe di connessione basilare e non ottimizzata
// La classe di connessione si trova in Database.JDBC

public class MySQLCon {

	private static final String DB_SCHEMA;
	private static final String DB_DATABASE;
	private static final String DB_HOST;
	private static final String DB_NAME;
	private static final String DB_PORT;
	private static final String DB_USERNAME;
	private static final String DB_PASSWORD;
	private static final String DB_URL;

	static {
		DB_SCHEMA = "jdbc";
		DB_DATABASE = "mysql"; 
		DB_HOST = "localhost"; 
		// In alternativa DB_HOST = "127.0.0.1" per indicare la macchina locale;
		
		DB_PORT = "3306";
		DB_NAME = "formatemp_scuoladb"; // Cambiare con il nome del database

		// Se dovesse esser necessario le credenziali bisogna fornirle qui
		DB_USERNAME = "root";
		DB_PASSWORD = "";

		// Composizione della stringa di connessione a JDBC
		// Esempio di stringa di connessione finale:
		// jdbc:mysql://localhost:3306/formatemp_db
		DB_URL = DB_SCHEMA + ":" + DB_DATABASE + "://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME
				+ "?serverTimezone=UTC";
	}

	public static void main(String[] args) {

		Connection conn = null;

		try {
			// Driver obsoleto
			// Class.forName("com.mysql.jdbc.Driver");

			// Driver corretto
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Connessione al database con username e password
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			if (conn != null) {
				System.out.println("✅ Connessione effettuata con successo al database " + DB_NAME);
			} else {
				System.out.println("⚠️ Connessione fallita!");
			}

		} catch (SQLException e) {
			System.err.println("❌ Errore SQL: " + e.getMessage());
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			System.err.println("❌ Driver JDBC non trovato!");
			e.printStackTrace();
			
		} finally {
			// Chiudiamo la connessione solo se è stata aperta
			if (conn != null) {
				try {
					conn.close();
					System.out.println("🔌 Connessione chiusa correttamente.");
				} catch (SQLException e) {
					System.err.println("⚠️ Errore nella chiusura della connessione: " + e.getMessage());
				}
			}
		}
	}
}
