package se.jensen.daniela.danisshop.controller;

import se.jensen.daniela.danisshop.model.Product;

import java.util.List;

/*
 * ProductRepository Interface - Definierar hur vi lagrar och hämtar produkter

 * - Separerar dataåtkomst från affärslogik!!!!!
 * - Gör det enkelt att byta lagringssätt (minne, fil, databas)
 * - Vi kan ha olika implementationer utan att ändra övrig kod som min "Frame" ;P;P;P
gör koden flexibel och testbar =D
* ingehåller INGEN KOD endast krav ;D
 */


public interface ProductRepository {
    void addProduct(Product product);

    Product getProduct(String articleNumber);

    List<Product> getAllProducts();

}
