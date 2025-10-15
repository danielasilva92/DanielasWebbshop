package se.jensen.daniela.danisshop.model;

// en av subklasserna av Product
public class Snacks extends Product {
    public Snacks(String title, String articleNumber, double price, String description) {
        super(title, articleNumber, price, description);
    }

    @Override
    public String category() {
        return "Food";
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
