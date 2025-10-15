package se.jensen.daniela.danisshop.model;

/* definerar vad en produkt ska vara
 * detta är min kontrakt
 * som är överflödig ;P;P;P
 * men har kvar den ändå för att påminna mig om det
 * man behöver faktist inte ha 39735789938728 klasser men JA de jag de
 * */

public interface ProductInterface {
    String category();

    String getTitle();

    String getDescription();

    double getPrice();

    String getArticleNumber();

    String toFileFormat();
}
