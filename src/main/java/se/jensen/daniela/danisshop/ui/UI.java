package se.jensen.daniela.danisshop.ui;
//definerar hur vi ska kommunicera med användaren

public interface UI {
    String prompt(String message);
    // ber användaren om input och returnerar den

    void info(String message);//visar en infomeddelande

    String menu(); // visar en menymeddelande

    String ProductMenu(); // meny för mina produkter
}
