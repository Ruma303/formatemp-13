package Esercizio;

public class Main {

	public static void main(String[] args) {

		MySQL db = new MySQL("127.0.0.1", "3306", "formatemp_scuoladb", "root", "");

		int scelta;
		do {
			System.out.println("1. Aggiungi studente");
			System.out.println("2. Visualizza studenti");
			System.out.println("3. Esci");
			do {
				scelta = IO.getInt("Cosa vuoi fare?");
				if (scelta < 1 || scelta > 3) {
					System.out.println("Devi scegliere tra 1 e 3");
				}
			} while (scelta < 1 || scelta > 3);
			switch (scelta) {

			case 1:
				System.out.println("Inserisci i dati dello studente");
				String nome = IO.getString("Nome");
				String cognome = IO.getString("Cognome");
				int eta = IO.getInt("Eta");

				String data_nascita;
				do {
					data_nascita = IO.getString("Data di nascita (gg/mm/aaaa)");
					data_nascita = Date.convertDateToISO(data_nascita);
					if (data_nascita.isEmpty()) {
						System.out.println("❌ DATA NON VALIDA! Inserisci nuovamente la data.");
					}
				} while (data_nascita.isEmpty()); // Continua finché l'utente non inserisce una data valida

				int id_corso = IO.getInt("ID Corso");

				if (db.InsertProtectedQuery(
						"INSERT INTO studenti (nome, cognome, eta, data_nascita, id_corso) VALUES (?, ?, ?, ?, ?)",
						new String[] { nome, cognome, String.valueOf(eta), data_nascita, String.valueOf(id_corso) })) {
					System.out.println("Studente inserito correttamente");
					System.out.println("ID generato: " + db.getLastInsertId());
				} else {
					System.out.println("Errore nell'inserimento dello studente!");
				}
				break;

			case 2:
				if (db.SelectQuery("SELECT * FROM studenti")) {
					while (db.next()) {
						System.out.println(db.getString("id_studente") + 
								" - " + db.getString("nome") + 
								" " + db.getString("cognome") + " " + db.getString("eta") 
								+ " " + Date.convertISOToDate(db.getString("data_nascita")) 
								+ " " + db.getString("id_corso"));
					}
				}
				break;
			}
		} while (scelta != 3);
		System.out.println("Arrivederci");
	}
}