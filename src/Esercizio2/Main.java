package Esercizio2;

public class Main {

	public static void main(String[] args) {

		IO.pl("Connessione al database ok.");

		// Crea un nuovo libro e lo salva
        Libro l1 = new Libro("Il Signore degli Anelli", "J.R.R. Tolkien", "Bompiani");
        l1.save();  // Questo inserisce il libro nel database

        // Aggiorna un libro esistente con ID 1
        Libro l2 = new Libro(1, "1984", "George Orwell", "Mondadori");
        l2.save();  // Questo aggiorna il libro con ID 1

        // Prova ad aggiornare un libro con ID inesistente
        Libro l3 = new Libro(999, "Libro Fantasma", "Autore Ignoto", "Nessun Editore");
        l3.save();  // Questo dovrebbe mostrare un messaggio di errore

	}
}
