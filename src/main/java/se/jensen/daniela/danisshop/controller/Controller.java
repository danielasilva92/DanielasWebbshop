package se.jensen.daniela.danisshop.controller;

/*
 * ProductController - Kontrollerar produktlogik och koordinerar UI + Repository
 * DETTA ÄR HJÄRNAN i applikationen!!!!
 * - UI hanterar användarinteraktion
 * - Repository hanterar datalagring
 * - Controller koordinerar dessa och innehåller affärslogik
 */


import se.jensen.daniela.danisshop.data.FileHandler;
import se.jensen.daniela.danisshop.model.Book;
import se.jensen.daniela.danisshop.model.Coffee;
import se.jensen.daniela.danisshop.model.Product;
import se.jensen.daniela.danisshop.model.Snacks;
import se.jensen.daniela.danisshop.ui.ConsoleUI;

import java.util.List;


public class Controller {
    private ProductRepository repository;
    private ConsoleUI console;

    /* Constructor med dependency injection
     * alltså skickar vi in beroendena via konstruktorn*/
    public Controller(ProductRepository repository, ConsoleUI console) {
        this.repository = repository;
        this.console = console;


    }

    // min huvudloop, här startar programmet
    public void startAdmin() {
        Boolean keepRunning = true;
        while (keepRunning) {
            String choice = console.menu();
            switch (choice) {
                case "1":
                    addProduct();
                    break;
                case "2":
                    getAllProducts();
                    break;
                case "3":
                    getProduct();
                    break;
                case "4":
                    saveProductsToFile();
                    break;
                case "5":
                    loadProductsFromFile();
                    break;
                case "6":
                    console.info("Tack för att du valde Danielas shop, välkommen åter!");
                    keepRunning = false;
                    break;
                default:
                    console.info("felaktigt val");
            }
        }
        console.closeScanner();
    }

    // metoden som man kan skapa en produkt
    public void addProduct() {
        boolean validChoice = false;
        String choice = "";
        while (!validChoice) {

            System.out.println("  ➕ LÄGG TILL NY PRODUKT ➕");
            System.out.println("    Vänligen välj kategori: ");
            choice = console.ProductMenu();

            if (choice.equals("4")) {
                console.info("Tillbaka till start");
                return;
            }
            // Validera valet INNAN vi frågar efter produktinformation
            if (choice.equals("1") ||
                    choice.equals("2") ||
                    choice.equals("3")) {
                validChoice = true;
            } else {
                console.info("❌ Ogiltigt val! Välj 1, 2, 3 eller 4.");

            }
        }
        //skriver ut text och inväntar svar från användaren
        String title = console.prompt("\uD83C\uDFF7\uFE0F Title");
        String articleNumber = console.prompt("\uD83D\uDCCC Article number");
        String description = console.prompt(" \uD83D\uDCDD Description");
        String priceP = console.prompt("\uD83D\uDCB0Price (kr)");


        // lägger in felhantering för priset
        try {
            double price = Double.parseDouble(priceP);// gör om svaret till ett tal
            Product product = null;

            switch (choice) {
                case "1":
                    product = new Book(title, articleNumber, price, description);
                    break;
                case "2":
                    product = new Coffee(title, articleNumber, price, description);
                    break;
                case "3":
                    product = new Snacks(title, articleNumber, price, description);
                    break;

                default:
                    console.info("❌ogiltigt val....");
                    return;
            }
            repository.addProduct(product); // lägger till produkten i lagret
            console.info("✅FÖLJANDE PRODUKT HAR LAGT TILL :"
                    + product.toFileFormat());
        } catch (NumberFormatException e) {
            console.info("❌felaktigt val av pris");
        }
    }

    // hämtar alla produkter från lagret och skriver ut dessa!
    public void getAllProducts() {
        List<Product> products = repository.getAllProducts();
        if (products.isEmpty()) {
            console.info("Inga produkter hittade⚠");
            return;
        }
        console.info("Antal produkter: " + repository.getAllProducts().size());
        System.out.println("---------------------------");

        for (int i = 0; i < products.size(); i++) {
            ;
            console.info(i + 1 + ". ");
            System.out.println(products.get(i));
            System.out.println("---------------------------");

        }
    }

    // söker efter en produkt med ett specifikt artikelnummer
    public void getProduct() {
        console.info("\uD83D\uDD0D Visa en specifik produkt");
        String articleNumber = console.prompt("Ange artikel nummer:");
        articleNumber = articleNumber.trim();
        Product product = repository.getProduct(articleNumber);// letar upp rätt objekt

        if (product == null) {
            console.info("❌Produkten hittades INTE med  följande artikelnummer:" + articleNumber);

        } else {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("║            ✨ PRODUKTINFORMATION ✨                ║");
            System.out.println("╠════════════════════════════════════════════════════╣");
            System.out.println("║ 📌 Artikelnummer: " + product.getArticleNumber());
            System.out.println("║ 🏷️  Titel: " + product.getTitle());
            System.out.println("║ 💰 Pris: " + product.getPrice() + " kr");
            System.out.println("║ 📝 Beskrivning: " + product.getDescription());
            System.out.println("║ 🏪 Kategori: " + product.category());
            System.out.println("╚════════════════════════════════════════════════════╝\n");
        }


    }
    /*save och load metoderna kallar på fileHandler som skriver/läser från
     * textfilen*/

    private void saveProductsToFile() {
        List<Product> products = repository.getAllProducts();
        FileHandler.saveProducts(products);// denna tar listan och skriver den till filen
        console.info("Produkten har sparats till filen");
        console.info("Antal produkter: " + products.size());
    }

    private void loadProductsFromFile() {
        List<Product> loadedProducts = FileHandler.loadProducts();
        if (loadedProducts.isEmpty()) {
            console.info("❌Inga produkter hittade i filen!!!");
        } else {

            console.info("Antal produkter: " + loadedProducts.size());
            System.out.println(loadedProducts);

        }


    }

}







