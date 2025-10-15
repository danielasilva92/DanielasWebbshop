package se.jensen.daniela.danisshop.model;

// en av subklasserna av Product
public class Book extends Product {

    /*konstruktor med super() för att kunna skicka vidare information till Product konstruktorn.*/
    public Book(String title, String articleNumber, double price, String description) {
        super(title, articleNumber, price, description);
    }

    @Override
    public String category() {
        return "Books";
    }

    @Override
    public String toFileFormat() {
        return super.toFileFormat();
    }

    @Override
    public String toString() {
        return String.format("📌 Art.nr: %s | 🏷️  Titel: %s | 💰 Pris: %.2f kr | 📝 Beskrivning: %s | 🏪 Kategori: %s",
                getArticleNumber(), getTitle(), getPrice(), getDescription(), category());
    }

}
