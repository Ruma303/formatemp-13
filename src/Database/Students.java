package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * TODO: Recuperare nome corso usando le join
 */

public class Students {

	public static void all() {
	    if (isEmpty()) {
	        System.out.println("La tabella studenti è vuota");
	        return;
	    }
	    String query = "SELECT * FROM studenti";

	    try (Connection conn = JDBC.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query);
	         ResultSet rs = pstmt.executeQuery()) {

	        DateTimeFormatter italianFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	        while (rs.next()) {
	            LocalDate date = rs.getDate("data_nascita") != null ? rs.getDate("data_nascita").toLocalDate() : null;
	            String formattedDate = (date != null) ? date.format(italianFormat) : "N/A";

	            System.out.println("ID: " + rs.getInt("id_studente") +
	                               ", Nome: " + rs.getString("nome") +
	                               ", Cognome: " + rs.getString("cognome") +
	                               ", Età: " + rs.getInt("eta") +
	                               ", Data di Nascita: " + formattedDate +
	                               ", ID Corso: " + rs.getInt("id_corso"));
	        }

	    } catch (SQLException e) {
	        System.err.println("❌ Errore SQL: " + e.getMessage());
	    }
	}

	public static void get(int id) {
		if (Students.isEmpty()) {
			System.out.println("La tabella studenti è vuota");
			return;
		}
		String query = "SELECT * FROM studenti WHERE id_studente = ?";

		try (Connection conn = JDBC.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					System.out.println("ID: " + rs.getInt("id_studente") + ", Nome: " + rs.getString("nome")
							+ ", Cognome: " + rs.getString("cognome") + ", Età: " + rs.getInt("eta") + ", ID Corso: "
							+ rs.getInt("id_corso"));
				} else {
					System.out.println("⚠️ Nessuno studente trovato con ID: " + id);
				}
			}

		} catch (SQLException e) {
			System.err.println("❌ Errore SQL: " + e.getMessage());
		}
	}

	public static void getByName(String nome) {
		if (Students.isEmpty()) {
			System.out.println("La tabella studenti è vuota");
			return;
		}
		String query = "SELECT * FROM studenti WHERE nome = ?";

		try (Connection conn = JDBC.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, nome);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					System.out.println("ID: " + rs.getInt("id_studente") + ", Nome: " + rs.getString("nome")
							+ ", Cognome: " + rs.getString("cognome") + ", Età: " + rs.getInt("eta") + ", ID Corso: "
							+ rs.getInt("id_corso"));
				}
			}

		} catch (SQLException e) {
			System.err.println("❌ Errore SQL: " + e.getMessage());
		}
	}

	public static void add(String nome, String cognome, int eta, String dateIT, int id_corso) {
		String dateISO = convertDateToISO(dateIT);
        if (dateISO == null) return; // Se la data è errata, interrompe
	    String query = "INSERT INTO studenti (nome, cognome, eta, data_nascita, id_corso) VALUES (?, ?, ?, ?, ?)";

	    try (Connection conn = JDBC.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setString(1, nome);
	        pstmt.setString(2, cognome);
	        pstmt.setInt(3, eta);
	        pstmt.setString(4, dateISO); // Conversione formato data
	        pstmt.setInt(5, id_corso);

	        int rowsAffected = pstmt.executeUpdate();
	        System.out.println("✅ Studente aggiunto in fondo alla lista.");

	    } catch (SQLException e) {
	        System.err.println("❌ Errore SQL: " + e.getMessage());
	    }
	}

	public static void add(int id_studente, String nome, String cognome, int eta, String dateIT, int id_corso, boolean append) {
		String dateISO = convertDateToISO(dateIT);
        if (dateISO == null) return; // Se la data è errata, interrompe
		if (exists(id_studente)) {
	        if (append) {
	            System.out.println("⚠️ Esiste già uno studente con ID " + id_studente + ". Verrà aggiunto in fondo.");
	            add(nome, cognome, eta, dateIT, id_corso); // Aggiunge senza specificare ID
	            return;
	        } else {
	            System.out.println("⚠️ Esiste già uno studente in questa posizione. Operazione annullata.");
	            return;
	        }
	    }

	    String query = "INSERT INTO studenti (id_studente, nome, cognome, eta, data_nascita, id_corso) VALUES (?, ?, ?, ?, ?, ?)";

	    try (Connection conn = JDBC.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setInt(1, id_studente);
	        pstmt.setString(2, nome);
	        pstmt.setString(3, cognome);
	        pstmt.setInt(4, eta);
	        pstmt.setString(5, dateISO);
	        pstmt.setInt(6, id_corso);

	        int rowsAffected = pstmt.executeUpdate();
	        System.out.println("✅ Studente inserito nella posizione specificata.");

	    } catch (SQLException e) {
	        System.err.println("❌ Errore SQL: " + e.getMessage());
	    }
	}

	public static void update(int id, String nome, String cognome, int eta, String dateIT, int id_corso) {
	    String dateISO = convertDateToISO(dateIT);
	    if (dateISO == null) return; // Se la data è errata, interrompe
	    
	    if (isEmpty()) {
	        System.out.println("La tabella studenti è vuota");
	        return;
	    }

	    String query = "UPDATE studenti SET nome = ?, cognome = ?, eta = ?, data_nascita = ?, id_corso = ? WHERE id_studente = ?";

	    try (Connection conn = JDBC.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setString(1, nome);
	        pstmt.setString(2, cognome);
	        pstmt.setInt(3, eta);
	        pstmt.setString(4, dateISO); 
	        pstmt.setInt(5, id_corso);
	        pstmt.setInt(6, id);

	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("✅ Studente aggiornato con successo.");
	        } else {
	            System.out.println("⚠️ Nessuno studente trovato con ID: " + id);
	        }

	    } catch (SQLException e) {
	        System.err.println("❌ Errore SQL: " + e.getMessage());
	    }
	}

	public static void delete(int id) {
		if (Students.isEmpty()) {
			System.out.println("La tabella studenti è vuota");
			return;
		}
		String query = "DELETE FROM studenti WHERE id_studente = ?";

		try (Connection conn = JDBC.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, id);

			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("✅ Studente eliminato con successo.");
			} else {
				System.out.println("⚠️ Nessuno studente trovato con ID: " + id);
			}

		} catch (SQLException e) {
			System.err.println("❌ Errore SQL: " + e.getMessage());
		}
	}

	public static void first() {
		if (Students.isEmpty()) {
			System.out.println("La tabella studenti è vuota");
			return;
		}
		String query = "SELECT * FROM studenti ORDER BY id_studente ASC LIMIT 1";

		try (Connection conn = JDBC.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				System.out.println("ID: " + rs.getInt("id_studente") + ", Nome: " + rs.getString("nome") + ", Cognome: "
						+ rs.getString("cognome") + ", Età: " + rs.getInt("eta"));
			} else {
				System.out.println("⚠️ Nessun studente trovato.");
			}

		} catch (SQLException e) {
			System.err.println("❌ Errore SQL: " + e.getMessage());
		}
	}

	public static void last() {
		if (Students.isEmpty()) {
			System.out.println("La tabella studenti è vuota");
			return;
		}
		String query = "SELECT * FROM studenti ORDER BY id_studente DESC LIMIT 1";

		try (Connection conn = JDBC.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				System.out.println("ID: " + rs.getInt("id_studente") + ", Nome: " + rs.getString("nome") + ", Cognome: "
						+ rs.getString("cognome") + ", Età: " + rs.getInt("eta"));
			} else {
				System.out.println("⚠️ Nessun studente trovato.");
			}

		} catch (SQLException e) {
			System.err.println("❌ Errore SQL: " + e.getMessage());
		}
	}

	public static int count() {
	    String query = "SELECT COUNT(*) FROM studenti";
	    try (Connection conn = JDBC.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query);
	         ResultSet rs = pstmt.executeQuery()) {
	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        System.err.println("❌ Errore SQL: " + e.getMessage());
	    }
	    return 0;
	}

	public static boolean isEmpty() {
	    return count() == 0;
	}

	public static boolean exists(int id) {
	    String query = "SELECT 1 FROM studenti WHERE id_studente = ?";
	    try (Connection conn = JDBC.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setInt(1, id);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            return rs.next();
	        }
	    } catch (SQLException e) {
	        System.err.println("❌ Errore SQL: " + e.getMessage());
	    }
	    return false;
	}
	
	public static String convertDateToISO(String dateIT) {
	    Scanner scanner = new Scanner(System.in);
	    
	    while (true) {
	        try {
	            DateTimeFormatter italianFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	            DateTimeFormatter isoFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            LocalDate date = LocalDate.parse(dateIT, italianFormat);
	            return date.format(isoFormat);
	        } catch (DateTimeParseException e) {
	            System.err.println("❌ Errore: Formato data non valido. Usa il formato gg/mm/aaaa.");
	            System.out.print("Inserisci nuovamente la data: ");
	            dateIT = scanner.nextLine();
	        }
	    }
	}
 }