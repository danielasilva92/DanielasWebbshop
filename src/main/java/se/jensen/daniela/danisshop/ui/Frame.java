package se.jensen.daniela.danisshop.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import se.jensen.daniela.danisshop.controller.ProductRepository;
import se.jensen.daniela.danisshop.data.FileHandler;
import se.jensen.daniela.danisshop.data.FileProductRepository;
import se.jensen.daniela.danisshop.model.Book;
import se.jensen.daniela.danisshop.model.Coffee;
import se.jensen.daniela.danisshop.model.Product;
import se.jensen.daniela.danisshop.model.Snacks;

import java.util.List;

/*Detta är min GUI klass, den grafiska delen av programmet med javafx
 * den jobbar tsm med Controller och Modelen(produkterna) */
public class Frame extends Application {
    private ProductRepository repository; /*min "databas" som hanterar produkterna
    kommunicerar med FileProductRepository & FileHandler*/
    private Stage primstage;// huvudfönstret

    /* Lägger in mina valda färger i hex format
     * hittat på oracle*/
    private static final String PRIMARY_COLOR = "#6C63FF";      // Lila
    private static final String SECONDARY_COLOR = "#4CAF50";    // Grön
    private static final String ACCENT_COLOR = "#FF6B6B";       // Röd/Rosa
    private static final String DARK_BG = "#1E1E2E";            // Mörk bakgrund
    private static final String CARD_BG = "#2D2D44";            // Kort bakgrund
    private static final String TEXT_COLOR = "#FFFFFF";         // Vit text

    /*Börjar med att bygga själva fönstret(stage)
     * sedan det som visas i fönstret(scen)
     * sen layouten som bestämmer hur det ser ut(VBox)
     * texten gör jag med Label
     * skapar knappar med Button
     * rutor där man kan skriva med TextField
     * setonAction bestämmer vad som händer när man klickar på knappen
     * */

    // start metoden som startar programmet
    @Override
    public void start(Stage stage) {
        this.primstage = stage;
        this.repository = new FileProductRepository();
        stage.setTitle("Danis webbshop Administration =D exx");
        stage.show();


        primstage.setTitle("Danis webbshop Administration =D");
        primstage.setScene(createMainScen());
        primstage.setWidth(1000);
        primstage.setHeight(800);
        primstage.show();

    }

    // skapar min huvudscen, denna bygger hela fönstret
    private Scene createMainScen() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + DARK_BG + ";");

        Scene scene = new Scene(root);

        VBox header = createHeader();
        root.setTop(header);

        //center med Knappar
        VBox center = createmainMenu();
        root.setCenter(center);

        // footer( texten som står längst nere typ
        Label footer = new Label("DANIELAS WEBBSHOP");
        footer.setTextFill(Color.web("#888888"));
        footer.setFont(Font.font("Arial", 12));
        footer.setPadding(new Insets(10));
        root.setBottom(footer);
        return scene;
    }

    private VBox createmainMenu() {
        VBox center = new VBox(20);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(45));


        //skapar flera knappar
        Button buttonAdd = createButton("➕ Lägg till produkt", PRIMARY_COLOR);
        Button buttonList = createButton("📋 Lista alla produkter", SECONDARY_COLOR);
        Button buttonSearch = createButton("🔍 Sök produkt", "#FFA726");
        Button buttonSave = createButton("💾 Spara till fil", "#42A5F5");
        Button buttonLoad = createButton("📂 Ladda från fil", "#AB47BC");
        Button buttonExit = createButton("🚪 Avsluta", ACCENT_COLOR);

        // event handlers när man trycker på en knapp så körs metoden i showaddproductdialog
        buttonAdd.setOnAction(e -> showAddProductDialog());
        buttonList.setOnAction(e -> showAllProductsDialog());
        buttonLoad.setOnAction(e -> loadFromFile());
        buttonSearch.setOnAction(e -> showSearchDialog());
        buttonSave.setOnAction(e -> saveToFile());
        buttonExit.setOnAction(e -> primstage.close());
        center.getChildren().addAll(buttonAdd, buttonList, buttonSearch, buttonSave, buttonLoad, buttonExit);
        return center;
    }

    // visar en popup med produktinformationen
    private void showProductDetails(Product product) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Produktdetaljer");
        alert.setHeaderText(product.getTitle());

        String details = String.format(
                "Artikelnummer: %s\n" +
                        "Kategori: %s\n" +
                        "Pris: %.2f kr\n" +
                        "Beskrivning: %s",
                product.getArticleNumber(),
                product.category(),
                product.getPrice(),
                product.getDescription()

        );
        alert.setContentText(details);
        alert.showAndWait();
    }

    // jobbar med filehandler
    private void saveToFile() {
        List<Product> products = repository.getAllProducts();
        FileHandler.saveProducts(products);
        showAlert("Sparat till fil", "Produkter har sparats till filen", Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /*visar en textdialgon som man kan skriva in artikelnummer
     * sedan letar efter produkt med detta artikelnummer*/
    private void showSearchDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Sök efter produkt");
        dialog.setHeaderText("Ange artikelnummer: ");
        dialog.setContentText("Artikelnummer: ");

        dialog.showAndWait().ifPresent(artnr -> {
            Product product = repository.getProduct(artnr);// här letar den efter prodkuten
            if (product == null) {
                showAlert("Inga produkt hittade", "Inga produkt hittade med artikelnummer: " + artnr, Alert.AlertType.INFORMATION);
            } else {
                showProductDetails(product);
            }
        });


    }

    // jobbar med filehandler
    private void loadFromFile() {
        List<Product> loadedProducts = FileHandler.loadProducts();
        if (loadedProducts.isEmpty()) {
            showAlert("Inga produkter hittade", "Inga produkter hittade i filen", Alert.AlertType.INFORMATION);

        } else {
            this.repository = new FileProductRepository();

            showAlert("Framgång",
                    "✅ " + loadedProducts.size() + " produkter har laddats från fil!",
                    Alert.AlertType.INFORMATION);
        }

    }

    // skapar nytt fönster som visar alla produkter i Listview
    private void showAllProductsDialog() {
        List<Product> products = repository.getAllProducts();
        if (products.isEmpty()) {
            showAlert("Inga produkter hittade", "Inga produkter hittade", Alert.AlertType.INFORMATION);
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("ALLA PRODUKTER");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: " + DARK_BG + ";");

        Label header = new Label("ALLA PRODUKTER (" + products.size() + "st)");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font("Arial", FontWeight.BOLD, 25));

        ListView<String> listView = new ListView<>();
        listView.setPrefSize(500, 400);
        listView.setStyle("-fx-background-color: " + CARD_BG + "; -fx-text-fill: white;");

        for (Product product : products) {
            listView.getItems().add(product.toString());
        }
        root.getChildren().addAll(header, listView);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();


    }

    /*den mest avancerande metoden!!!!!!!!
     * skapar formulär för att lägga till ny produkt
     * anävnder GridPane där jag kan placera
     * comboBox för kategori
     * textfield för artikelnummer, titel etc
     * */
    private void showAddProductDialog() {
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Lägg till produkt");
        dialog.setHeaderText("Fyll i produktinformationen");

        //folmulär
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: " + "#FF6B6B" + ";" + "-fx-text-fill: white;");
        grid.setHgap(100);
        grid.setVgap(100);
        grid.setPadding(new Insets(100));

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.setPrefWidth(300);
        categoryBox.getItems().addAll("Books", "Food", "Drinks");
        categoryBox.setPromptText("Välj Kategori");

        TextField articleNumber = new TextField();
        articleNumber.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        articleNumber.setPromptText("artikelnummer");

        TextField title = new TextField();
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setPromptText("Titel");

        TextField price = new TextField();
        price.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        price.setPromptText("Pris i kr");

        TextField description = new TextField();
        description.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        description.setPromptText("Beskrivning");


        grid.add(new Label("Kategori"), 0, 0);
        grid.add(categoryBox, 1, 0);
        grid.add(new Label("Artikelnummer"), 0, 1);
        grid.add(articleNumber, 1, 1);
        grid.add(new Label("Titel"), 0, 2);
        grid.add(title, 1, 2);
        grid.add(new Label("Pris"), 0, 3);
        grid.add(price, 1, 3);
        grid.add(new Label("Beskrivning"), 0, 4);
        grid.add(description, 1, 4);
        dialog.getDialogPane().setContent(grid);

        ButtonType okButtonType = new ButtonType("Lägg till", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == okButtonType) {
                String category = categoryBox.getValue();
                if (category == null || category.isEmpty()) {
                    showAlert("FEL", " välj en kategori", Alert.AlertType.ERROR);
                    return null;
                }
                try {

                    double pt = Double.parseDouble(price.getText());

                    Product product = null;
                    switch (category) {
                        case "Books":
                            product = new Book(title.getText(), articleNumber.getText(), pt, description.getText());
                            break;
                        case "Food":
                            product = new Snacks(title.getText(), articleNumber.getText(), pt, description.getText());
                            break;
                        case "Drinks":
                            product = new Coffee(title.getText(), articleNumber.getText(), pt, description.getText());
                            break;
                        default:
                            showAlert("felaktigt val", "Ogiltig kategori", Alert.AlertType.ERROR);
                            break;
                    }
                    return product;

                } catch (NumberFormatException e) {
                    showAlert("felaktigt fortmat", "Ogiltig pris", Alert.AlertType.ERROR);
                    return null;
                }

            }
            return null;
        });
        dialog.showAndWait().ifPresent(product -> {
            repository.addProduct(product);
            showAlert("Lyckades", "Produkt har lagts till", Alert.AlertType.INFORMATION);
        });
    }

    /*en metod för att skapa knappar med samma stil
     * färg, storlek, font
     * hover effet så knappen ändrar färg när man har musen över den*/
    private Button createButton(String s, String primaryColor) {
        Button button = new Button(s);
        button.setMinWidth(450);
        button.setMinHeight(50);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        button.setStyle("-fx-background-color: " + primaryColor + ";" +
                "-fx-background-radius: 10;" +
                "-fx-cursor: hand;");
        button.setTextFill(Color.WHITE);

        // hover effekt
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(" + primaryColor + ", 20%);" +
                "-fx-cursor: hand;" + "-fx-background-radius: 10;" +
                "-fx-cursor: hand;" + "-fx-scale-x: 1.05; -fx-scale-y: 1.05"));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: " + primaryColor + ";" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;"
        ));
        return button;
    }

    /*bygger rubriken högst upp
     * namnet på applikationen
     * VBoc för layout
     * labeö för texten
     * */
    private VBox createHeader() {
        VBox header = new VBox(20);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(30, 20, 30, 20));
        header.setStyle("-fx-background-color: linear-gradient(to right, " + PRIMARY_COLOR + ", " + ACCENT_COLOR + ");");

        Label title = new Label("DANIELAS WEBBSHOP");
        title.setTextFill(Color.web(TEXT_COLOR));
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setTextFill(Color.WHITE);

        Label subtitle = new Label("Administration");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        subtitle.setTextFill(Color.web("#E0E0E0"));
        header.getChildren().addAll(title, subtitle);
        return header;
    }

    // denna kör programmert
    public static void main(String[] args) {
        launch();// startar fönstersystemet
    }
}
