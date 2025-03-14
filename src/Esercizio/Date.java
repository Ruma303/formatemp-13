package Esercizio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Date {
	
	public static String convertDateToISO(String dateIT) {
		while (true) {
			try {
				DateTimeFormatter italianFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				DateTimeFormatter isoFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date = LocalDate.parse(dateIT, italianFormat);
				return date.format(isoFormat);
			} catch (DateTimeParseException e) {
				System.err.println("❌ Errore: Formato data non valido. Usa il formato gg/mm/aaaa.");
				System.out.print("Inserisci nuovamente la data: ");
				return "";
			}
		}
	}

	public static String convertISOToDate(String dateISO) {
		if (dateISO == null || dateISO.isEmpty())
			return "";
		try {
			DateTimeFormatter italianFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			DateTimeFormatter isoFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(dateISO, isoFormat);
			return date.format(italianFormat);
		} catch (DateTimeParseException e) {
			System.err.println("❌ Errore: Formato data non valido. Usa il formato aaaa-mm-gg.");
			return "";
		}
	}
}