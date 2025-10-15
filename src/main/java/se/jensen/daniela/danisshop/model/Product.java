package se.jensen.daniela.danisshop.model;

/* en abstrakt klass som alla mina produkter ärver ifrån*/
public abstract class Product implements ProductInterface {
    private String title;
    private String description;
    private double price;
    private String articleNumber;

    //konstruktor
    public Product(String title, String articleNumber, double price, String description) {
        this.title = title;
        this.articleNumber = articleNumber;
        this.price = price;
        this.description = description;
    }

    // även här en abstrakt klass som varje subklass ska implementera
    public abstract String category();

    //getters ;)
    @Override
    public String getTitle() {
        return "Titel:" + title;
    }

    @Override
    public String getDescription() {
        return "Info: " + description + "";
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getArticleNumber() {
        return articleNumber;
    }

    // format för att skriva ut i filen
    @Override
    public String toFileFormat() {
        return category() + ";" + articleNumber + ";" + title + ";" + price + ";" + description;
    }

    // skriver ut produktinfo
    @Override
    public String toString() {
        return "Title: " + title + " | Price: " + price + " | Description: " + description + " | Category: " + category() + " | Article number: " + articleNumber + "";

    }

}
