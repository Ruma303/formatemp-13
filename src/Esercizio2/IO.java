package Esercizio2;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Questa è una classe di utilità per ridefinire i metodi principali di Java e
 * creare nuovi metodi personalizzati
 */
public class IO {

    // Costante per riferirsi alla classe stessa
    protected static final Class<?> SELF;
    private static Boolean error;
    private static Scanner scanner = new Scanner(System.in);

    static {
        SELF = IO.class;
        error = false;
    }

    // Crea solo una nuova linea (Print line)
    public static void pl() {
        System.out.println("\n");
    }

    // Ridefiniamo il metodo in modo che prenda un oggetto come parametro
    // e lo stampi a video formattandolo come vogliamo.
    public static void pl(Object obj) {
        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            for (Object o : list) {
                System.out.println(o); // Usa toString() invece di castare a String
            }
        } else {
            System.out.println(obj);
        }
    }

    // Stampa con tabulazione e nuova linea
    public static void pt(Object o) {
        System.out.println("\t" + o);
    }

    public static void saluta(Object o) {
        System.out.println("Ciao " + o + "!\n");
    }

    // Separatore di linea
    public static void sepL() {
        System.out.println("\n=================================================\n");
    }

    // Ritorna il nome della classe di un oggetto
    public static String getClassName(Object o) {
        return o.getClass().getName();
    }

    // Ritorna la classe di un oggetto
    public static Object getClass(Object o) {
        return o.getClass();
    }

    /**
     * @param path = Percorso relativo
     */
    public static void readF(String path) {
        Path filePath = Paths.get(path);

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (NoSuchFileException e) {
            System.out.println("Errore: Il file '" + filePath.toAbsolutePath() + "' non esiste.");
        } catch (IOException e) {
            System.out.println("Errore durante la lettura del file: " + e.getMessage());
        }
    }

    public static void list(Object obj) {
        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            for (int i = 0; i < list.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + list.get(i));
            }
        } else if (obj.getClass().isArray()) {
            Object[] array = (Object[]) obj;
            for (int i = 0; i < array.length; i++) {
                System.out.println("\t" + (i + 1) + ". " + array[i]);
            }
        } else {
            System.out.println(obj);
        }
    }

    /**
     * Legge un numero intero dall'utente, gestendo errori di input.
     * @return Integer inserito dall'utente
     */
    public static Integer getInt() {
        while (true) {
            try {
                Integer res = scanner.nextInt();
                scanner.nextLine(); // Consuma la linea rimanente
                return res;
            } catch (InputMismatchException e) {
                System.out.println("Devi inserire un numero intero.");
                scanner.nextLine(); // Pulisce l'input errato
            }
        }
    }

    /**
     * Stampa un messaggio e legge un numero intero dall'utente.
     * @param msg Messaggio da mostrare
     * @return Integer inserito dall'utente
     */
    public static Integer getInt(String msg) {
        pl(msg);
        return getInt();
    }

    /**
     * Legge un numero decimale dall'utente, gestendo errori di input.
     * @return Double inserito dall'utente
     */
    public static Double getDouble() {
        while (true) {
            try {
                Double res = scanner.nextDouble();
                scanner.nextLine(); // Consuma la linea rimanente
                return res;
            } catch (InputMismatchException e) {
                System.out.println("Devi inserire un numero decimale.");
                scanner.nextLine(); // Pulisce l'input errato
            }
        }
    }

    /**
     * Stampa un messaggio e legge un numero decimale dall'utente.
     * @param msg Messaggio da mostrare
     * @return Double inserito dall'utente
     */
    public static Double getDouble(String msg) {
        pl(msg);
        return getDouble();
    }

    /**
     * Legge una stringa dall'utente.
     * @return Stringa inserita dall'utente
     */
    public static String getString() {
        return scanner.nextLine();
    }

    /**
     * Stampa un messaggio e legge una stringa dall'utente.
     * @param msg Messaggio da mostrare
     * @return Stringa inserita dall'utente
     */
    public static String getString(String msg) {
        pl(msg);
        return getString();
    }

    /**
     * Legge un valore booleano dall'utente, gestendo errori di input.
     * @return Boolean inserito dall'utente
     */
    public static Boolean getBoolean() {
        while (true) {
            try {
                Boolean res = scanner.nextBoolean();
                scanner.nextLine(); // Consuma la linea rimanente
                return res;
            } catch (InputMismatchException e) {
                System.out.println("Devi inserire 'true' o 'false'.");
                scanner.nextLine(); // Pulisce l'input errato
            }
        }
    }

    /**
     * Stampa un messaggio e legge un valore booleano dall'utente.
     * @param msg Messaggio da mostrare
     * @return Boolean inserito dall'utente
     */
    public static Boolean getBoolean(String msg) {
        pl(msg);
        return getBoolean();
    }

    /**
     * Fa attendere il programma per un numero specificato di secondi.
     * @param seconds Numero di secondi da attendere
     */
    public static void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Attesa interrotta.");
        }
    }
    
    /**
     * Chiude lo Scanner per liberare le risorse.
     */
    public static void close() {
        scanner.close();
    }
    
    /**
     * Saluta e chiude lo Scanner.
     */
    public static void exit() {
    	IO.pl("Arrivederci!");
    	IO.close();
    	System.exit(0);
    }
    
    /**
     * Saluta e chiude lo Scanner.
     * @param msg Messaggio da mostrare durante la saluto
     */
    public static void exit(String msg) {
    	IO.pl(msg);
    	IO.close();
    	System.exit(0);
    }
    
    /**
     * Avverte l'utente.
     */
    public static void warn() {
    	IO.pl("*** Attenzione! ***");
    }
    
    /**
     * Avverte l'utente.
     * @param msg Messaggio da mostrare durante la saluto
     */
    public static void warn(String msg) {
    	IO.pl(msg);
    }
}