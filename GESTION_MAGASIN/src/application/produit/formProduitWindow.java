package application.produit;

import java.time.ZoneId;
import java.util.Date;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class formProduitWindow{

    VBox root=new VBox();
    HBox buttonsBox=new HBox();

    Scene scene=new Scene(root);
    Stage window=new Stage();
    Label  titleLabel=new Label("Ajouter un Produit");
    Label idLabel = new Label("ID:");
    TextField idTextField = new TextField();
    Label produitDesignationLael=new Label("Designation:");
    TextField produitDesignationtextField=new TextField();
    Label produitPrixLabel=new Label("Prix:");
    TextField produitPrixtextField=new TextField();
    Label produitQteLabel=new Label("Quantité:");
    TextField produitQtetextField=new TextField();
    Label produitDateLabel=new Label("Date:");
    DatePicker produitDatetextField=new DatePicker();
    Button produitADDBoutton=new Button("Ajouter");
    Button produitCancelBoutton=new Button("Annuler");
  
    

    private void initWindow(){
        window.setScene(scene);
        // Obtenir la taille de l'écran principal
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        // Définir la taille de la fenêtre en fonction de la taille de l'écran
        window.setWidth(screenWidth ); // Taille de la fenêtre réduite à 80% de la largeur de l'écran
        window.setHeight(screenHeight); // Taille de la fenêtre réduite à 80% de la hauteur de l'écran

        window.setTitle("Nouveau produit");
        window.getIcons().add(new Image("file:logo.png"));
        window.initModality(Modality.APPLICATION_MODAL);
    }
    private void addNodesToWindow(){
        root.getChildren().add(titleLabel);
        root.getChildren().addAll(idLabel, idTextField);
        root.getChildren().addAll(produitDesignationLael,produitDesignationtextField);
        root.getChildren().addAll(produitPrixLabel,produitPrixtextField);
        root.getChildren().addAll(produitQteLabel,produitQtetextField);
        root.getChildren().addAll(produitDateLabel,produitDatetextField);
        buttonsBox.getChildren().addAll(produitADDBoutton,produitCancelBoutton);
        root.getChildren().add(buttonsBox);
    }
    
    private  void addStylesToNodes(){
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        titleLabel.getStyleClass().add("LabelTitle");
        titleLabel.setMinWidth(window.getWidth());
        idLabel.getStyleClass().add("LabelForm");
        produitDesignationLael.getStyleClass().add("LabelForm");
        produitPrixLabel.getStyleClass().add("LabelForm");
        produitQteLabel.getStyleClass().add("LabelForm");
        produitDateLabel.getStyleClass().add("LabelForm");
        produitADDBoutton.getStyleClass().add("menuButton");
        produitCancelBoutton.getStyleClass().add("menuButton");
        buttonsBox.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        buttonsBox.setSpacing(20);
        root.getStyleClass().add("fen");
    }
    
    private  void addEvents(){
        produitCancelBoutton.setOnAction(event->{
            window.close();
        });
        produitADDBoutton.setOnAction(event->{
        	Long id= Long.parseLong(idTextField.getText());
        	String designation = produitDesignationtextField.getText();
        	double prix = Double.parseDouble(produitPrixtextField.getText());
        	int quantite = Integer.parseInt(produitQtetextField.getText());
        	Date date = Date.from(produitDatetextField.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        	Produit produit = new Produit(id,designation, prix, quantite, date);
            ProductManagementDao dao = new ProductManagementDao();
            dao.add(produit);
        });
        //ne quitter pas la fenetre juste par annuler
        window.setOnCloseRequest(event->{
            event.consume();
        });
    
    }
    public formProduitWindow(){
        initWindow();
        addStylesToNodes();
        addNodesToWindow();
        addEvents();
        window.show();
}
}

