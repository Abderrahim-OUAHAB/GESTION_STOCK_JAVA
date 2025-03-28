package application.paiement;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.client.Client;
import application.client.ClientManagementDao;
import application.facture.Facture;
import application.facture.PdfFacture;
import application.produit.ProductManagementDao;
import application.produit.Produit;
import application.vente.Vente;
import application.vente.VenteManagementDao;
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

public class formPaiementsWindow {
	 VBox root=new VBox();
	    HBox buttonsBox=new HBox();
	    Scene scene=new Scene(root);
	    Stage window=new Stage();
	    Label  titleLabel=new Label("Ajouter une paiement");
	    Label idLabel = new Label("ID:");
	    TextField idTextField = new TextField();
	    Label idClientLabel = new Label("ID Client:");
	    TextField idClientTextField = new TextField();
	    Label pDateLabel=new Label("Date Paiement:");
	    DatePicker pDatetextField=new DatePicker();
	    Label methodeLabel = new Label("Methode paiement:");
	    TextField methodeTextField = new TextField();
	    Label statutLabel=new Label("Statut de paiement:");
	    TextField statuttextField=new TextField();
	
	    Button pADDBoutton=new Button("Ajouter");
	    Button pCancelBoutton=new Button("Annuler");
	  

	    private void initWindow(){
	        window.setScene(scene);
	        Screen screen = Screen.getPrimary();
	        double screenWidth = screen.getBounds().getWidth();
	        double screenHeight = screen.getBounds().getHeight();

	        // Définir la taille de la fenêtre en fonction de la taille de l'écran
	        window.setWidth(screenWidth ); // Taille de la fenêtre réduite à 80% de la largeur de l'écran
	        window.setHeight(screenHeight); // Tail
	        window.setTitle("Nouveau paiement");
	        window.getIcons().add(new Image("file:I.png"));
	        window.initModality(Modality.APPLICATION_MODAL);
	    }
	    private void addNodesToWindow(){
	        root.getChildren().add(titleLabel);
	        root.getChildren().addAll(idLabel, idTextField);
	        root.getChildren().addAll(idClientLabel,idClientTextField);
	        root.getChildren().addAll(pDateLabel,pDatetextField);
	        root.getChildren().addAll(methodeLabel,methodeTextField);
	        root.getChildren().addAll(statutLabel,statuttextField);
	        buttonsBox.getChildren().addAll(pADDBoutton,pCancelBoutton);
	        root.getChildren().add(buttonsBox);
	    }
	    
	    private  void addStylesToNodes(){
	        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	        titleLabel.getStyleClass().add("LabelTitle");
	        titleLabel.setMinWidth(window.getWidth());
	        idLabel.getStyleClass().add("LabelForm");
	        idClientLabel.getStyleClass().add("LabelForm");
	        pDateLabel.getStyleClass().add("LabelForm");
	        methodeLabel.getStyleClass().add("LabelForm");
	        statutLabel.getStyleClass().add("LabelForm");
	        pADDBoutton.getStyleClass().add("menuButton");
	        pCancelBoutton.getStyleClass().add("menuButton");
	        buttonsBox.setAlignment(Pos.CENTER);
	        root.setSpacing(20);
	        buttonsBox.setSpacing(20);
	        root.getStyleClass().add("fen");
	    }
	    
	    private  void addEvents(){
	        pCancelBoutton.setOnAction(event->{
	            window.close();
	        });
	        pADDBoutton.setOnAction(event->{
	        	Long id= Long.parseLong(idTextField.getText());
	        	Long id_client = Long.parseLong(idClientTextField.getText());
	        	Date date = Date.from(pDatetextField.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	        	String me = methodeTextField.getText();
	        	String s = statuttextField.getText();
	    
	        	Paiement v = new Paiement(id,id_client,date, me, s);
	            PaiementsManagementDao dao = new  PaiementsManagementDao();
	            dao.add(v);
	            ClientManagementDao co=new ClientManagementDao();
	        	Client c=co.getOne(id_client);
	        	  double m=0.0;
	        	 List<Long> ps=new ArrayList<>();
	        	 List<Produit> prs=new ArrayList<>();
	        	 List<Vente>vrs=new ArrayList<>();
	              VenteManagementDao d=new  VenteManagementDao();
	              List<Vente> vs=d.getAll();
	              for(Vente vv:vs) {
	              	if(vv.getClientId()==id_client) {
	              		m+=v.getMontant();
	              		ps.add(vv.getProduitId());
	              		vrs.add(vv);
	              	}
	              }
	              ProductManagementDao doo=new ProductManagementDao();
	              for(Long l:ps) {
	        	for(Produit p:doo.getAll()) {
	        		
	        			if(p.getId()==l) {
	        				prs.add(p);
	        			}
	        		}
	        	}
	        	Instant instant = date.toInstant();
	    		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
	    		String formattedDate = localDate.format(DateTimeFormatter.ISO_DATE);
	        	Facture f =new Facture(id_client,c,formattedDate,m,prs,vrs);
	        	PdfFacture pdf=new PdfFacture();
	        	 pdf.exportProductsToPdf("PDF_MODEL/modele_f.pdf", "PDF/facture.pdf", f);
	 
	        });
	        //ne quitter pas la fenetre juste par annuler
	        window.setOnCloseRequest(event->{
	            event.consume();
	        });
	    
	    }
	    public formPaiementsWindow(){
	        initWindow();
	        addStylesToNodes();
	        addNodesToWindow();
	        addEvents();
	        window.show();
	}
}
