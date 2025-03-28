package application.vente;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import application.paiement.Paiement;
import application.paiement.PaiementsManagementDao;
import application.produit.ProductManagementDao;
import application.produit.Produit;

import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

public class VentesListWindow {
	 Stage window=new Stage();
	    VBox root=new VBox();
	    Scene scene =new Scene(root);
	    HBox botot=new HBox();
	    Label titleLabel =new Label("Liste des ventes");
	    HBox totalHbox=new HBox();
	    HBox buttonsBox=new HBox();
	    Label totalLabel=new Label("Total :");
	    Label totalValue=new Label("0");
	    TableColumn<Vente,Long> IDcolumn=new TableColumn<>("Id");
	    TableColumn<Vente,Long> clicolumn=new TableColumn<>("Id Client");
	    TableColumn<Vente,Long> prodcolumn=new TableColumn<>("Id Produit");
	    TableColumn<Vente,Integer> qtecolumn=new TableColumn<>("Quantité");
	    TableColumn<Vente,Double> totalcolumn=new TableColumn<>("Montant");
	    TableColumn<Vente,LocalDate> datecolumn=new TableColumn<>("Date de vente");
	    TableColumn<Vente, Void> actionCol = new TableColumn<>("Actions");
	    
	    
	    TableView<Vente> venteTableView=new TableView<>();
	    ObservableList<Vente> venteObservablelist=FXCollections.observableArrayList();
	    Button produitPdfBoutton=new Button("Télécharger PDF");
	    Button dashboardButton = new Button("Dashboard");
	    //ajouter des clomns aux table
	    @SuppressWarnings("unchecked")
		private void addColmnTotableView(){
	        venteTableView.getColumns().addAll(IDcolumn,clicolumn,prodcolumn,qtecolumn,totalcolumn,datecolumn,actionCol);
	        venteTableView.setItems(venteObservablelist);
	    }
	    
	    private void updateColumns(){
	        IDcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
	        IDcolumn.setPrefWidth(100);
	        clicolumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
	        clicolumn.setPrefWidth(250);
	        prodcolumn.setCellValueFactory(new PropertyValueFactory<>("produitId"));
	        prodcolumn.setPrefWidth(150);
	        qtecolumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
	        qtecolumn.setPrefWidth(150);
	        totalcolumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
	        totalcolumn.setPrefWidth(150);
	        datecolumn.setCellValueFactory(new PropertyValueFactory<>("dateVente"));
	        datecolumn.setPrefWidth(200);
	        // Ajouter la colonne d'actions
	        actionCol.setCellFactory(new Callback<TableColumn<Vente, Void>, TableCell<Vente, Void>>() {
	            @Override
	            public TableCell<Vente, Void> call(final TableColumn<Vente, Void> param) {
	                final TableCell<Vente, Void> cell = new TableCell<Vente, Void>() {

	                	  Button btnEdit = new Button("Modifier");
	                	    Button btnDelete = new Button("Supprimer");

	                    {
	                        btnEdit.setOnAction(event -> {
	                        	Vente produit = getTableView().getItems().get(getIndex());
	                            handleEditAction(produit);
	                        });

	                        btnDelete.setOnAction(event -> {
	                        	Vente produit = getTableView().getItems().get(getIndex());
	                            handleDeleteAction(produit);
	                        });
	                    }

	                    HBox pane = new HBox(btnEdit, btnDelete);

	                    @Override
	                    public void updateItem(Void item, boolean empty) {
	                        super.updateItem(item, empty);
	                        if (empty) {
	                            setGraphic(null);
	                        } else {
	                            setGraphic(pane);
	                        }
	                    }
	                };
	                return cell;
	            }
	        });
	        actionCol.setPrefWidth(200);
	        datecolumn.setPrefWidth(200);
	    }
	    private void handleEditAction(Vente produit) {
	    	EditVenteWindow ep=	 new EditVenteWindow(produit);
	    	  ep.showAndWait();
	    	  
	    	    calculTotal();
	    	    venteTableView.refresh();
	    	
	    }
	    

	    private void handleDeleteAction(Vente produit) {
	        // Logique pour supprimer le produit
	    	   PaiementsManagementDao da=new  PaiementsManagementDao ();
	    	   List<Paiement> pms=new ArrayList<>();
	    	   pms=da.getAll();
	    	   for(Paiement pm:pms) {
	    		   if(pm.getCliId()==produit.getClientId()) {
	    			   da.delete(pm.getId());;
	    		   }
	    	   }
	    	 ProductManagementDao d=new ProductManagementDao();
	    	  Produit p=d.getOne(produit.getProduitId());
	    	   d.update(new Produit(p.getId(),p.getDesignation(),p.getPrix(),p.getQte()+produit.getQuantite(),Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())));
	        venteObservablelist.remove(produit);
	        VenteManagementDao dao=new  VenteManagementDao ();
	        dao.delete(produit.getId());
	        calculTotal(); // Recalcule le total après la suppression
	    }
	    
	    private void calculTotal(){
	        Double total=0.0;
	        for(Vente p:venteObservablelist ) {total+=p.getMontant();}
	        totalValue.setText(total+" DH");
	    }
	    private void getProduits() {
	        VenteManagementDao dao = new VenteManagementDao();
	        List<Vente> produits = dao.getAll();
	        venteObservablelist.addAll(produits);
	    }
	    private void addStylesToNodes(){
	        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	        titleLabel.getStyleClass().add("LabelTitle");
	        titleLabel.setMinWidth(window.getWidth());
	        totalLabel.getStyleClass().add("LabelTotal");
	        totalValue.getStyleClass().add("LabelTotal");
	        totalHbox.getStyleClass().add("menuButton");
	        venteTableView.getStyleClass().add("table-row-cell");
	        titleLabel.setMinWidth(window.getWidth());
	       // venteTableView.setMinHeight(500);
	        totalHbox.setSpacing(15);
	        produitPdfBoutton.getStyleClass().add("menuButton");
	        dashboardButton.getStyleClass().add("menuButton");

	        root.getStyleClass().add("fen");
	    }
	    private void initWindow(){
	    	  Screen screen = Screen.getPrimary();
	          double screenWidth = screen.getBounds().getWidth();
	          double screenHeight = screen.getBounds().getHeight();

	          // Définir la taille de la fenêtre en fonction de la taille de l'écran
	          window.setWidth(screenWidth ); // Taille de la fenêtre réduite à 80% de la largeur de l'écran
	          window.setHeight(screenHeight); 
	        window.setTitle("La liste des ventes");
	        window.getIcons().add(new Image("file:logo.png"));
	        window.setScene(scene);

	    }
	    private void addNodesTopane(){
	    	 totalHbox.getChildren().addAll(totalLabel,totalValue);
	         root.getChildren().addAll(titleLabel,venteTableView);
	         buttonsBox.getChildren().addAll(produitPdfBoutton,dashboardButton);
	      
	         HBox spacer = new HBox();
	         HBox.setHgrow(spacer, Priority.ALWAYS);
	         
	         botot.getChildren().addAll(totalHbox, spacer, buttonsBox);
	         botot.setSpacing(10); // Optionnel : ajoute un petit espace entre les éléments et le spacer
	         root.getChildren().add(botot);
	    }
	    
	    private  void addEvents(){
	    	produitPdfBoutton.setOnAction(event->{
	    		
	    		 PdfVentes pdf=new  PdfVentes();
	    		 pdf.exportProductsToPdf("PDF_MODEL/modele_v.pdf", "PDF/ventes.pdf", venteObservablelist);
	    		
	    	});
	    	   dashboardButton.setOnAction(event -> {
	    	       
	    		   	new DashboardVentesWindow(venteObservablelist);
	    	      

	    	    });
	    
	    }
	    public VentesListWindow() {
	        initWindow();
	        updateColumns();
	        addColmnTotableView();
	        getProduits();
	        calculTotal();
	        addNodesTopane();
	        addStylesToNodes();
	        addEvents();
	        window.show();
	    }
}
