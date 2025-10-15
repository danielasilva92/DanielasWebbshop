package se.jensen.daniela.danisshop;

import se.jensen.daniela.danisshop.controller.Controller;
import se.jensen.daniela.danisshop.data.FileProductRepository;
import se.jensen.daniela.danisshop.ui.ConsoleUI;

public class Main {
    // min völkomstmeddelande som visas FÖRST i programmet
    private static void welcome() {
        System.out.println("❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿");
        System.out.println("                  ♡♡♡ VÄLKOMMEN TILL ♡♡♡");
        System.out.println("    ♡♡♡♡♡♡♡♡♡♡♡♡ D A N I E L A S W E B B S H O P ♡♡♡♡♡♡♡♡♡");
        System.out.println("❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿ ❀ ✿");
    }

    /*skriver ut välkommen
     * skapar konsolgränssnitt
     * skapar ett lager (FileProductRepository)
     * skapar controller som kopplar ihop dessa
     * startAdmin startar programmet*/
    public static void main(String[] args) {
        welcome();
        ConsoleUI ui = new ConsoleUI();

        Controller controller = new Controller(new FileProductRepository(), ui);
        controller.startAdmin();


    }
}
