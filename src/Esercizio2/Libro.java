package Esercizio2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Libro {

	/*
	 * Inziate con progetto LIBRERIA •⁠ X ⁠create una classe Libro con la
	 * possibilità di inserire id, titolo, autore, editore. •⁠ X ⁠id è Integer •⁠ X
	 * ⁠gli altri sono strighe •⁠ X ⁠Di base il libro si istianzia con un
	 * costruttore senza parametri, dove mette l'id a null e gli altri a strighe
	 * vuote •⁠ X ⁠Prevedete anche un costruttore in cui inserite i campi ma non
	 * l'id e quello lo lasciate null, per ora •⁠ ? ⁠Prevedete anche un costruttore
	 * in cui inserite tutti i campi , compreso un id intero •⁠ X ⁠Prevedete i
	 * metodi get e set •⁠ ⁠prevedete un metodo save, dove se l'id è nullo allora fa
	 * un insert altrimenti fa un update
	 */
	/*
	 * db> describe libri;
	 * +---------+--------------+------+-----+---------+----------------+ | Field |
	 * Type | Null | Key | Default | Extra |
	 * +---------+--------------+------+-----+---------+----------------+ | id | int
	 * | NO | PRI | NULL | auto_increment | | titolo | varchar(500) | YES | | NULL |
	 * | | autore | varchar(500) | YES | | NULL | | | editore | varchar(500) | YES |
	 * | NULL | | +---------+--------------+------+-----+---------+----------------+
	 */

	private Integer id;
	private String titolo;
	private String autore;
	private String editore;

	public void save() {
	    if (this.id == null) {
	        // Insert
	        String query = "INSERT INTO libri (titolo, autore, editore) VALUES (?, ?, ?)";

	        try (Connection conn = MySQL.getConnection(); 
	             PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

	            pstmt.setString(1, titolo);
	            pstmt.setString(2, autore);
	            pstmt.setString(3, editore);

	            int rowsAffected = pstmt.executeUpdate();
	            System.out.println("Libro aggiunto: " + this.titolo + " con ID " + this.id);

	        } catch (SQLException e) {
	            System.err.println("❌ Errore SQL: " + e.getMessage());
	        }
	    } else {
	        // Update
	        String query = "UPDATE libri SET titolo = ?, autore = ?, editore = ? WHERE id = ?";

	        try (Connection conn = MySQL.getConnection(); 
	             PreparedStatement pstmt = conn.prepareStatement(query)) {

	            pstmt.setString(1, titolo);
	            pstmt.setString(2, autore);
	            pstmt.setString(3, editore);
	            pstmt.setInt(4, id);  // WHERE id = ?

	            int rowsAffected = pstmt.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Libro aggiornato: " + this.titolo);
	            } else {
	                System.out.println("❌ Nessun libro trovato con ID: " + this.id);
	            }

	        } catch (SQLException e) {
	            System.err.println("❌ Errore SQL: " + e.getMessage());
	        }
	    }
	}

	public Libro() {
		this.id = null;
		this.titolo = "";
		this.autore = "";
		this.editore = "";
	}

	public Libro(int id) {
		this.id = id;
		this.titolo = "";
		this.autore = "";
		this.editore = "";
	}

	public Libro(Integer id, String titolo, String autore, String editore) {
		super();
		this.id = id;
		this.titolo = titolo;
		this.autore = autore;
		this.editore = editore;
	}

	public Libro(String titolo, String autore, String editore) {
		super();
		this.id = null;
		this.titolo = titolo;
		this.autore = autore;
		this.editore = editore;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public String getEditore() {
		return editore;
	}

	public void setEditore(String editore) {
		this.editore = editore;
	}
}
