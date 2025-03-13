package App;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import Database.Students;

public class Main {
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("🔗 Connessione al database...");
		System.out.println("\n");
		System.out.println("*** Benvenuto nella gestione della scuola! ***");
	    System.out.println("\n");

// 		Test query statiche
//		System.out.println("Recupero statico studente con id_studente = 2: ");
//		Students.get(2);         	
//		System.out.println("\n");

//		System.out.println("Recupero studente cercando per nome: ");
//		Students.getByName("Mattia"); 
//		System.out.println("\n");

	  
	    boolean exit = false;

	    // Definire menu 1: seleziona risorse: studenti, insegnanti, corsi
	    // Menu 2: operazioni sulle singole risorse
	    do {
	        menu();
	        System.out.print("Scegli un'opzione: ");
	        int input = sc.nextInt();
	        sc.nextLine();

	        switch (input) {
	            case 1: getAllStudents(); break;
	            case 2: getStudentById(); break;
	            case 3: getStudentByName(); break;
	            case 4: createStudent(); break;
	            case 5: setStudent(); break;
	            case 6: updateStudent(); break;
	            case 7: deleteStudent(); break;
	            case 0: exit(); exit = true; break;
	            default: System.out.println("Input non valido!\n"); break;
	        }
	    } while (!exit);
	}
	
	public static void menu() {
		System.out.println("Digita:");
		System.out.println("\t1: vedere tutti gli studenti");
		System.out.println("\t2: trovare uno studente per id");
		System.out.println("\t3: trovare uno studente per nome");
		System.out.println("\t4: aggiungere uno studente");
		System.out.println("\t5: aggiungere uno studente in una determinata posizione");
		System.out.println("\t6: modificare uno studente");
		System.out.println("\t7: eliminare uno studente");
		System.out.println("\t0: per uscire");
		System.out.println("\n");
	}

	public static void getAllStudents() {
		System.out.println("Recupero " + Students.count() + " studenti...");
		Students.all();
		System.out.println("\n");
	}

	public static void getStudentById() {
		System.out.println("Ricerca nel campo id_studenti...");
		System.out.print("Inserisci un numero: ");
		String input = sc.nextLine();
		Integer id = Integer.parseInt(input);

		System.out.println("Ricerco studente con id_studente = " + id);
		Students.get(id);
		System.out.println("\n");
	}

	public static void getStudentByName() {
		System.out.println("Ricerca nel campo nome...");
		System.out.print("Inserisci un nome: ");
		String input = sc.nextLine();

		System.out.println("Ricerco studente con nome: " + input);
		Students.getByName(input);
		System.out.println("\n");
	}

	public static void createStudent() {
	    System.out.println("Creazione studente...");

	    System.out.print("Inserisci nome: ");
	    String name = sc.nextLine();

	    System.out.print("Inserisci cognome: ");
	    String lastname = sc.nextLine();

	    System.out.print("Inserisci età: ");
	    int eta = sc.nextInt();
	    sc.nextLine(); 

	    System.out.print("Inserisci data di nascita (gg/mm/aaaa): ");
	    String dateIT = sc.nextLine();

	    System.out.print("Aggiungi corso (id_corso): ");
	    int course = sc.nextInt();
	    sc.nextLine();

	    Students.add(name, lastname, eta, dateIT, course);
	}

	// Inserimento in posizione specifica
	public static void setStudent() {
	    System.out.println("Creazione studente in posizione specifica...");

	    System.out.print("Inserisci posizione (ID studente): ");
	    int id = sc.nextInt();
	    sc.nextLine();

	    if (Students.exists(id)) {
	        System.out.print("⚠️ Esiste già uno studente con ID " + id + ". Vuoi aggiungerlo in fondo? (sì/no): ");
	        String response = sc.nextLine().trim().toLowerCase();

	        if (!response.equals("si") && !response.equals("s") && !response.equals("y") && !response.equals("yes")) {
	            System.out.println("❌ Operazione annullata.");
	            return;
	        }
	    }

	    System.out.print("Inserisci nome: ");
	    String name = sc.nextLine();

	    System.out.print("Inserisci cognome: ");
	    String lastname = sc.nextLine();

	    System.out.print("Inserisci età: ");
	    int eta = sc.nextInt();
	    sc.nextLine();

	    System.out.print("Inserisci data di nascita (gg/mm/aaaa): ");
	    String dateIT = sc.nextLine();

	    System.out.print("Aggiungi corso (id_corso): ");
	    int course = sc.nextInt();
	    sc.nextLine();

	    if (Students.exists(id)) {
	        Students.add(name, lastname, eta, dateIT, course); // Aggiunge in fondo
	    } else {
	        Students.add(id, name, lastname, eta, dateIT, course, false); // Aggiunge nella posizione specificata
	    }
	}

	// Aggiorna studente
	public static void updateStudent() {
	    System.out.println("Aggiornamento studente...");

	    System.out.print("Inserisci ID studente da aggiornare: ");
	    int id = sc.nextInt();
	    sc.nextLine();

	    if (!Students.exists(id)) {
	        System.out.println("❌ Non esiste uno studente con questo ID!");
	        return;
	    }

	    System.out.print("Inserisci nome: ");
	    String name = sc.nextLine();

	    System.out.print("Inserisci cognome: ");
	    String lastname = sc.nextLine();

	    System.out.print("Inserisci età: ");
	    int eta = sc.nextInt();
	    sc.nextLine();

	    System.out.print("Inserisci data di nascita (gg/mm/aaaa): ");
	    String dateIT = sc.nextLine();

	    System.out.print("Aggiungi corso (id_corso): ");
	    int course = sc.nextInt();
	    sc.nextLine();

	    Students.update(id, name, lastname, eta, dateIT, course);
	}
	
	// Elimina studente
	public static void deleteStudent() {
		System.out.println("Eliminazione studente...");

		int id;
		System.out.print("Inserisci posizione (ID studente) da eliminare: ");
		id = sc.nextInt();
		sc.nextLine();

		if (!Students.exists(id)) {
			System.out.print("Non esiste uno studente in questa posizione!");
			return;
		}
		Students.delete(id);
	}

	public static void exit() {
		System.out.println("Arrivederci!");
	}
}