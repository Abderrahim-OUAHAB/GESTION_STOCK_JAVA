package application.inventaire;

import java.time.ZoneId;
import java.util.Date;

import application.produit.ProductManagementDao;
import application.produit.Produit;
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

public class formInvWindow {
	  VBox root=new VBox();
	    HBox buttonsBox=new HBox();

	    Scene scene=new Scene(root);
	    Stage window=new Stage();
	    Label  titleLabel=new Label("Ajouter une inventaire");
	    Label idLabel = new Label("ID:");
	    TextField idTextField = new TextField();
	    Label produitIdLabel=new Label("ID Produit:");
	    TextField produitIdtextField=new TextField();
	    Label produitQteLabel=new Label("Quantité:");
	    TextField produitQtetextField=new TextField();
	    Label produitDateLabel=new Label("Date d'ajout:");
	    DatePicker produitDatetextField=new DatePicker();
	    Button produitADDBoutton=new Button("Ajouter");
	    Button produitCancelBoutton=new Button("Annuler");
	  

	    private void initWindow(){
	        window.setScene(scene);
	        Screen screen = Screen.getPrimary();
	        double screenWidth = screen.getBounds().getWidth();
	        double screenHeight = screen.getBounds().getHeight();

	        // Définir la taille de la fenêtre en fonction de la taille de l'écran
	        window.setWidth(screenWidth ); // Taille de la fenêtre réduite à 80% de la largeur de l'écran
	        window.setHeight(screenHeight); // Tail
	        window.setTitle("Nouveau inventaire");
	        window.getIcons().add(new Image("file:I.png"));
	        window.initModality(Modality.APPLICATION_MODAL);
	    }
	    private void addNodesToWindow(){
	        root.getChildren().add(titleLabel);
	        root.getChildren().addAll(idLabel, idTextField);
	        root.getChildren().addAll(produitIdLabel,produitIdtextField);
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
	        produitIdLabel.getStyleClass().add("LabelForm");
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
	        	Long designation = Long.parseLong(produitIdtextField.getText());
	        	int quantite = Integer.parseInt(produitQtetextField.getText());
	        	Date date = Date.from(produitDatetextField.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	        	Inventaire produit = new Inventaire(id,designation,quantite, date);
	            InvManagementDao dao = new  InvManagementDao();
	            dao.add(produit);
	            ProductManagementDao da = new ProductManagementDao();
	            Produit pv=da.getOne(designation);
	            da.update(new Produit(pv.getId(),pv.getDesignation(),pv.getPrix(),pv.getQte()+quantite,date));
	        });
	        //ne quitter pas la fenetre juste par annuler
	        window.setOnCloseRequest(event->{
	            event.consume();
	        });
	    
	    }
	    public formInvWindow(){
	        initWindow();
	        addStylesToNodes();
	        addNodesToWindow();
	        addEvents();
	        window.show();
	}
}
