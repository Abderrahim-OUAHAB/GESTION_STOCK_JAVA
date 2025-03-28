package application.vente;

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

public class formVenteWindow {
	 VBox root=new VBox();
	    HBox buttonsBox=new HBox();
	    Scene scene=new Scene(root);
	    Stage window=new Stage();
	    Label  titleLabel=new Label("Ajouter une Vente");
	    Label idLabel = new Label("ID:");
	    TextField idTextField = new TextField();
	    Label idClientLabel = new Label("ID Client:");
	    TextField idClientTextField = new TextField();
	    Label idProduitLabel = new Label("ID Produit:");
	    TextField idProduitTextField = new TextField();
	    Label produitQteLabel=new Label("Quantité:");
	    TextField produitQtetextField=new TextField();
	    Label VenteDateLabel=new Label("Date:");
	    DatePicker VenteDatetextField=new DatePicker();
	    Button VenteADDBoutton=new Button("Ajouter");
	    Button VenteCancelBoutton=new Button("Annuler");
	  

	    private void initWindow(){
	        window.setScene(scene);
	        Screen screen = Screen.getPrimary();
	        double screenWidth = screen.getBounds().getWidth();
	        double screenHeight = screen.getBounds().getHeight();

	        // Définir la taille de la fenêtre en fonction de la taille de l'écran
	        window.setWidth(screenWidth ); // Taille de la fenêtre réduite à 80% de la largeur de l'écran
	        window.setHeight(screenHeight); 
	        window.setTitle("Nouveau Vente");
	        window.getIcons().add(new Image("file:I.png"));
	        window.initModality(Modality.APPLICATION_MODAL);
	    }
	    private void addNodesToWindow(){
	        root.getChildren().add(titleLabel);
	        root.getChildren().addAll(idLabel, idTextField);
	        root.getChildren().addAll(idClientLabel,idClientTextField);
	        root.getChildren().addAll(idProduitLabel,idProduitTextField);
	        root.getChildren().addAll(produitQteLabel,produitQtetextField);
	        root.getChildren().addAll(VenteDateLabel,VenteDatetextField);
	        buttonsBox.getChildren().addAll(VenteADDBoutton,VenteCancelBoutton);
	        root.getChildren().add(buttonsBox);
	    }
	    
	    private  void addStylesToNodes(){
	        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	        titleLabel.getStyleClass().add("LabelTitle");
	        titleLabel.setMinWidth(window.getWidth());
	        idLabel.getStyleClass().add("LabelForm");
	        idClientLabel.getStyleClass().add("LabelForm");
	        idProduitLabel.getStyleClass().add("LabelForm");
	        produitQteLabel.getStyleClass().add("LabelForm");
	        VenteDateLabel.getStyleClass().add("LabelForm");
	        VenteADDBoutton.getStyleClass().add("menuButton");
	        VenteCancelBoutton.getStyleClass().add("menuButton");
	        buttonsBox.setAlignment(Pos.CENTER);
	        root.setSpacing(20);
	        buttonsBox.setSpacing(20);
	        root.getStyleClass().add("fen");
	    }
	    
	    private  void addEvents(){
	        VenteCancelBoutton.setOnAction(event->{
	            window.close();
	        });
	        VenteADDBoutton.setOnAction(event->{
	        	Long id= Long.parseLong(idTextField.getText());
	        	Long id_client = Long.parseLong(idClientTextField.getText());
	        	Long id_produit = Long.parseLong(idProduitTextField.getText());
	        	int quantite = Integer.parseInt(produitQtetextField.getText());
	        	Date date = Date.from(VenteDatetextField.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	        	Vente v = new Vente(id,id_client, id_produit, quantite, date);
	            VenteManagementDao dao = new VenteManagementDao();
	            dao.add(v);
	            ProductManagementDao d = new ProductManagementDao();
	            Produit p=d.getOne(id_produit);
	            d.update(new Produit(id_produit,p.getDesignation(),p.getPrix(),p.getQte()-quantite,date));
	        });
	        //ne quitter pas la fenetre juste par annuler
	        window.setOnCloseRequest(event->{
	            event.consume();
	        });
	    
	    }
	    public formVenteWindow(){
	        initWindow();
	        addStylesToNodes();
	        addNodesToWindow();
	        addEvents();
	        window.show();
	}
}
