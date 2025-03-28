package application;

import application.client.ClientsListWindow;
import application.client.formClientWindow;
import application.inventaire.InvListWindow;
import application.inventaire.formInvWindow;
import application.paiement.PaiementsListWindow;
import application.paiement.formPaiementsWindow;
import application.produit.ProduitsListWindow;
import application.produit.formProduitWindow;
import application.vente.VentesListWindow;
import application.vente.formVenteWindow;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainWindow extends Application {

    private BorderPane root = new BorderPane();
    private Scene scene = new Scene(root);

    // Buttons to replace MenuItems
    Button noveauProduitButton = createButton("Nouveau produit");
    Button listeProduitsButton = createButton("Liste des produits");
    Button noveauClientButton = createButton("Nouveau client");
    Button listeClientsButton = createButton("Liste des clients");
    Button nvventeButton = createButton("Nouvelle vente");
    Button listeVentesButton = createButton("Liste des ventes");
    Button nvpaiementButton = createButton("Nouveau paiement");
    Button listePaiementButton = createButton("Liste des paiements");
    Button nvinvButton = createButton("Nouvel inventaire");
    Button listeInvButton = createButton("Liste des inventaires");
    Button helpsupButton = createButton("Contact support");

    private Button createButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("menuButton");
        return button;
    }

    private void createMenu() {
        GridPane menuGrid = new GridPane();
        menuGrid.setAlignment(Pos.CENTER);
        menuGrid.setVgap(20);
        menuGrid.setHgap(40);
        menuGrid.setPadding(new Insets(40));

        // Add buttons to the grid with appropriate row and column indices
        menuGrid.add(noveauProduitButton, 0, 0);
        menuGrid.add(listeProduitsButton, 1, 0);
        menuGrid.add(noveauClientButton, 0, 1);
        menuGrid.add(listeClientsButton, 1, 1);
        menuGrid.add(nvventeButton, 0, 2);
        menuGrid.add(listeVentesButton, 1, 2);
        menuGrid.add(nvpaiementButton, 0, 3);
        menuGrid.add(listePaiementButton, 1, 3);
        menuGrid.add(nvinvButton, 0, 4);
        menuGrid.add(listeInvButton, 1, 4);
        menuGrid.add(helpsupButton, 0, 5, 2, 1); // span 2 columns for support button

        root.setCenter(menuGrid);

        // Apply fade-in animation to menuGrid
        FadeTransition fadeIn = new FadeTransition(Duration.millis(3000), menuGrid);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        // Apply hover and press effects to buttons
        applyButtonEffects(noveauProduitButton);
        applyButtonEffects(listeProduitsButton);
        applyButtonEffects(noveauClientButton);
        applyButtonEffects(listeClientsButton);
        applyButtonEffects(nvventeButton);
        applyButtonEffects(listeVentesButton);
        applyButtonEffects(nvpaiementButton);
        applyButtonEffects(listePaiementButton);
        applyButtonEffects(nvinvButton);
        applyButtonEffects(listeInvButton);
        applyButtonEffects(helpsupButton);
    }

    private void applyButtonEffects(Button button) {
        // Add hover effect
        button.setOnMouseEntered(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
        });

        button.setOnMouseExited(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.play();
        });

        // Add press effect
        button.setOnMousePressed(e -> {
            button.setEffect(new DropShadow(10, Color.GRAY));
        });

        button.setOnMouseReleased(e -> {
            button.setEffect(null);
        });
    }

    // Ajouter des styles
    private void addStylesToNodes() {
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        root.getStyleClass().add("mainWindow");
    }

    // Ajouter des événements
    private void addEvents() {
        noveauProduitButton.setOnAction(event -> new formProduitWindow());
        listeProduitsButton.setOnAction(event -> new ProduitsListWindow());
        noveauClientButton.setOnAction(event -> new formClientWindow());
        listeClientsButton.setOnAction(event -> new ClientsListWindow());
        nvventeButton.setOnAction(event -> new formVenteWindow());
        listeVentesButton.setOnAction(event -> new VentesListWindow());
        nvpaiementButton.setOnAction(event -> new formPaiementsWindow());
        listePaiementButton.setOnAction(event -> new PaiementsListWindow());
        nvinvButton.setOnAction(event -> new formInvWindow());
        listeInvButton.setOnAction(event -> new InvListWindow());
        helpsupButton.setOnAction(event -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Contact support");
            alert.setHeaderText("Contactez-nous");
            alert.setContentText(
                    "Fax     : 05-36-45-67-89 .\n" +
                    "Mobile  : 06-32-39-48-97 .\n" +
                    "Email   : support@wahhabshop.com .");
            alert.showAndWait();
        });
    }

    // Main
    public static void main(String[] args) {
        Application.launch(args);
    }

    // Window
    @Override
    public void start(Stage window) throws Exception {
        createMenu(); // créer le menu
        addEvents();
        addStylesToNodes(); // ajouter les styles CSS
      
        window.setScene(scene);
       
        // Obtenir la taille de l'écran principal
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        // Définir la taille de la fenêtre en fonction de la taille de l'écran
        window.setWidth(screenWidth ); // Taille de la fenêtre réduite à 80% de la largeur de l'écran
        window.setHeight(screenHeight); // Taille de la fenêtre réduite à 80% de la hauteur de l'écran

        window.setTitle("Gestion de magasin");
        window.getIcons().add(new Image("file:logo.png"));
        window.show();
    }
}

