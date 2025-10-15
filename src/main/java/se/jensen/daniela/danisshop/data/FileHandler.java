package se.jensen.daniela.danisshop.data;
/*Detta är en egen ansvarsområde som hanterar filen!
ska spara produkterna i en fil
hanterar läsning och skrivning till filen
 */

import se.jensen.daniela.danisshop.model.Book;
import se.jensen.daniela.danisshop.model.Coffee;
import se.jensen.daniela.danisshop.model.Product;
import se.jensen.daniela.danisshop.model.Snacks;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/* använder final så att värdet inte kan ändras.
 * och static så att den inte kan instansieras
 * tillhör alltså klassen, inte objektet och kan användas direkt
 * man behöver inte skapa ett filehandler objekt*/
public class FileHandler {
    private static final String SHOPPING = "products.txt";

    /*Sparar alla produkter till en fil
     * fileWriter öppnar själva filen
     * bufferedWriter skriver till filen( snabbre &effiktivare)
     * */
    public static void saveProducts(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SHOPPING))) {
            for (Product product : products) {// en enhanced loop (ForEach)
                writer.write(product.toFileFormat());// skriver en rad till filen
                writer.newLine();// går till nästa rad
            }
        } catch (Exception e) {
            System.out.println("❌ Fel vid sparande till fil: " + e.getMessage());
        }
    }


    public static List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        File file = new File(SHOPPING);
        if (!file.exists()) {
            System.out.println("Filen finns inte än!");
            return new ArrayList<>();
            
        }/*BufferdReader & fileReader används för att läsa från filen
        och har en felhantering så proggrammet inte kraschar hallå!*/
        try (BufferedReader reader = new BufferedReader(new FileReader(SHOPPING))) {
            String lines;
            while ((lines = reader.readLine()) != null) {
                String[] parts = lines.split(";");
                if (parts.length == 5) {
                    String catergory = parts[0];
                    String articleNumber = parts[1];
                    String title = parts[2];
                    double price = Double.parseDouble(parts[3]);
                    String description = parts[4];

                    Product product = null;
                    switch (catergory) {
                        case "Books":
                            product = new Book(title, articleNumber, price, description);
                            break;
                        case "Drinks":
                            product = new Coffee(title, articleNumber, price, description);
                            break;
                        case "Food":
                            product = new Snacks(title, articleNumber, price, description);
                            break;
                        default:
                            break;
                    }

                    if (product != null) {
                        products.add(product);

                    }
                }
            }
            if (!products.isEmpty()) {
                System.out.println("✅ " + products.size() + " produkter har laddats från " + SHOPPING);
            }


        } catch (IOException e) {
            System.out.println("❌ Fel vid läsning från fil!");
        }
        return products;
    }
}

