package se.jensen.daniela.danisshop.data;

import se.jensen.daniela.danisshop.controller.ProductRepository;
import se.jensen.daniela.danisshop.model.Product;

import java.util.ArrayList;
import java.util.List;
/*Detta är den "riktiga" lagringsklassen*/

public class FileProductRepository implements ProductRepository {
    private List<Product> products;// en lista med produkter

    //konstuktor
    public FileProductRepository() {
        this.products = new ArrayList<>();
        loadProductsFromFile(); // ladda prdukter automatisk när programmet startar

    }

    // laddar in filen från listan
    private void loadProductsFromFile() {
        products = FileHandler.loadProducts();

        if (products.isEmpty()) {
            //  System.out.println("Inga produkter hittade i filen");
        }
    }

    /*skriver ut listan till filen
     * Denna metoden är private för att
     * fileProductRepository är den ända som behöver denna
     * ingen annan ska kunna anropa den*/
    private void saveProductsToFile() {
        FileHandler.saveProducts(products);

    }

    /*varje gång en produkt läggs tills sparas den till filen*/
    @Override
    public void addProduct(Product product) {
        products.add(product);
        saveProductsToFile();// sparar automatiskt ;D

    }

    // söker igenom alla produkter i listan och returnerar matchande produkt
    @Override
    public Product getProduct(String articleNumber) {
        for (Product product : products) {
            if (product.getArticleNumber().equals(articleNumber)) {
                return product;
            }
        }
        return null;
    }

    /* visar listan och returnerar en kopia av den
     * detta för att ingen ska kunna ändra originallistan*/
    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);


    }
}
