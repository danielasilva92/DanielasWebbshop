package se.jensen.daniela.danisshop.ui;

import java.util.Scanner;

// denna klassen implemerar UI och sköterr all interaktion via konsolen
//lägger även in scanner här så kan jag använda den typ överallt ;P;P;P
public class ConsoleUI implements UI {
    Scanner scanner = new Scanner(System.in);

    public ConsoleUI() {
        this.scanner = scanner;
    }

    // samma sak här använder string prompt där vart det behövs istället för att skriva nextline hela tiden
    @Override
    public String prompt(String message) {
        System.out.print("💬 " + message + ": ");
        return scanner.nextLine();
    }

    @Override
    public void info(String message) {
        System.out.println("ℹ️  " + message);

    }

    @Override
    public String menu() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║              🌸 DANI’S WEBSHOP 🌸            ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║ 1. Lägg till produkt                         ║");
        System.out.println("║ 2. Lista alla produkter                      ║");
        System.out.println("║ 3. Visa produktinformation                   ║");
        System.out.println("║ 4. Spara produkter till fil                  ║");
        System.out.println("║ 5. Läs in produkter från fil                 ║");
        System.out.println("║ 6. Avsluta                                   ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.print("👉 Välj ett alternativ (1-6): ");
        return scanner.nextLine();
    }

    @Override
    public String ProductMenu() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       📦 VÄLJ PRODUKTTYP 📦            ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1️.  BÖCKER 📚📖                        ║");
        System.out.println("║ 2️.  SNACKS                             ║");
        System.out.println("║ 3️.  DRICKA ☕                          ║");
        System.out.println("║ 4️.  Gå tillbaka till huvudmeny          ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("👉 Välj produkttyp (1-4): ");
        return scanner.nextLine();
    }


    /* stänger objektet när programmet avslutas */
    public void closeScanner() {
        scanner.close();
    }

}


