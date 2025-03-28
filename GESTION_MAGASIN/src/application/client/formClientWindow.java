package application.client;
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
public class formClientWindow {
	 VBox root=new VBox();
	    HBox buttonsBox=new HBox();
	    Scene scene=new Scene(root);
	    Stage window=new Stage();
	    Label  titleLabel=new Label("Ajouter un Client");
	    Label idLabel = new Label("ID:");
	    TextField idTextField = new TextField();
	    Label clientPrenomLael=new Label("Prénom:");
	    TextField clientPrenomtextField=new TextField();
	    Label clientNomLabel=new Label("Nom:");
	    TextField clientNomtextField=new TextField();
	    Label clientTelLabel=new Label("Téléphone:");
	    TextField clientTeltextField=new TextField();
	    Button clientADDBoutton=new Button("Ajouter");
	    Button clientCancelBoutton=new Button("Annuler");
	    
	  

	    private void initWindow(){
	        window.setScene(scene);
	        Screen screen = Screen.getPrimary();
	        double screenWidth = screen.getBounds().getWidth();
	        double screenHeight = screen.getBounds().getHeight();
	        window.setWidth(screenWidth ); // Taille de la fenêtre réduite à 80% de la largeur de l'écran
	        window.setHeight(screenHeight);
	        window.setTitle("Nouveau client");
	        window.getIcons().add(new Image("file:I.png"));
	        window.initModality(Modality.APPLICATION_MODAL);
	    }
	    private void addNodesToWindow(){
	        root.getChildren().add(titleLabel);
	        root.getChildren().addAll(idLabel, idTextField);
	        root.getChildren().addAll(clientPrenomLael,clientPrenomtextField);
	        root.getChildren().addAll(clientNomLabel,clientNomtextField);
	        root.getChildren().addAll(clientTelLabel,clientTeltextField);
	        buttonsBox.getChildren().addAll(clientADDBoutton,clientCancelBoutton);
	        root.getChildren().add(buttonsBox);
	    }
	    
	    private  void addStylesToNodes(){
	        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	        titleLabel.getStyleClass().add("LabelTitle");
	        titleLabel.setMinWidth(window.getWidth());
	        idLabel.getStyleClass().add("LabelForm");
	        clientPrenomLael.getStyleClass().add("LabelForm");
	        clientNomLabel.getStyleClass().add("LabelForm");
	        clientTelLabel.getStyleClass().add("LabelForm");
	        clientADDBoutton.getStyleClass().add("menuButton");
	        clientCancelBoutton.getStyleClass().add("menuButton");
	        buttonsBox.setAlignment(Pos.CENTER);
	        root.setSpacing(20);
	        buttonsBox.setSpacing(20);
	        root.getStyleClass().add("fen");
	    }
	    
	    private  void addEvents(){
	        clientCancelBoutton.setOnAction(event->{
	            window.close();
	        });
	        clientADDBoutton.setOnAction(event->{
	        	Long id= Long.parseLong(idTextField.getText());
	        	String prenom = clientPrenomtextField.getText();
	        	String nom = clientNomtextField.getText();
	        	String tel = clientTeltextField.getText();
	        	
	        	Client c = new Client(id,prenom, nom, tel);
	            ClientManagementDao dao = new ClientManagementDao ();
	            dao.add(c);
	        });
	        //ne quitter pas la fenetre juste par annuler
	        window.setOnCloseRequest(event->{
	            event.consume();
	        });
	    
	    }
	    public formClientWindow(){
	        initWindow();
	        addStylesToNodes();
	        addNodesToWindow();
	        addEvents();
	        window.show();
	}
}
