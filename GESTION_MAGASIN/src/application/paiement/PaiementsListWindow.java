package application.paiement;

import java.time.LocalDate;
import java.util.List;

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

public class PaiementsListWindow {
	 Stage window=new Stage();
	    VBox root=new VBox();
	    Scene scene =new Scene(root);
	    HBox botot=new HBox();
	    Label titleLabel =new Label("Liste des paiements");
	    HBox totalHbox=new HBox();
	    HBox buttonsBox=new HBox();
	    Label totalLabel=new Label("Total :");
	    Label totalValue=new Label("0");
	    TableColumn<Paiement,Long> IDcolumn=new TableColumn<>("Id");
	    TableColumn< Paiement,Long> clicolumn=new TableColumn<>("Id Client");
	    TableColumn< Paiement,Double> prodcolumn=new TableColumn<>("Montant");
	    TableColumn< Paiement,LocalDate> qtecolumn=new TableColumn<>("Date de paiement");
	    TableColumn< Paiement,String> totalcolumn=new TableColumn<>("Méthode de paiement");
	    TableColumn< Paiement,String> datecolumn=new TableColumn<>("Statut de paiement");
	    TableColumn<Paiement, Void> actionCol = new TableColumn<>("Actions");
	    TableView< Paiement> venteTableView=new TableView<>();
	    ObservableList< Paiement> pObservablelist=FXCollections.observableArrayList();
	    Button produitPdfBoutton=new Button("Télécharger PDF");
	    Button dashboardButton = new Button("Dashboard");
		private void addColmnTotableView(){
	        venteTableView.getColumns().addAll(IDcolumn,clicolumn,prodcolumn,qtecolumn,totalcolumn,datecolumn,actionCol);
	        venteTableView.setItems(pObservablelist);
	    }
	    
	    private void updateColumns(){
	        IDcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
	        IDcolumn.setPrefWidth(100);
	        clicolumn.setCellValueFactory(new PropertyValueFactory<>("cliId"));
	        clicolumn.setPrefWidth(250);
	        prodcolumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
	        prodcolumn.setPrefWidth(150);
	        qtecolumn.setCellValueFactory(new PropertyValueFactory<>("datePaiement"));
	        qtecolumn.setPrefWidth(150);
	        totalcolumn.setCellValueFactory(new PropertyValueFactory<>("methodePaiement"));
	        totalcolumn.setPrefWidth(150);
	        datecolumn.setCellValueFactory(new PropertyValueFactory<>("statutPaiement"));
	        datecolumn.setPrefWidth(200);
	        // Ajouter la colonne d'actions
	        actionCol.setCellFactory(new Callback<TableColumn<Paiement, Void>, TableCell<Paiement, Void>>() {
	            @Override
	            public TableCell<Paiement, Void> call(final TableColumn<Paiement, Void> param) {
	                final TableCell<Paiement, Void> cell = new TableCell<Paiement, Void>() {

	                	  Button btnEdit = new Button("Modifier");
	                	    Button btnDelete = new Button("Supprimer");

	                    {
	                        btnEdit.setOnAction(event -> {
	                        	Paiement produit = getTableView().getItems().get(getIndex());
	                            handleEditAction(produit);
	                        });

	                        btnDelete.setOnAction(event -> {
	                        	Paiement produit = getTableView().getItems().get(getIndex());
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
	    }
	    private void handleEditAction(Paiement produit) {
	    	EditPaiementWindow ep=	 new EditPaiementWindow(produit);
	    	  ep.showAndWait();
	    	
	    	    calculTotal();
	    	    venteTableView.refresh();
	    }

	    private void handleDeleteAction(Paiement produit) {
	        // Logique pour supprimer le produit
	        pObservablelist.remove(produit);
	        PaiementsManagementDao dao=new  PaiementsManagementDao ();
	        dao.delete(produit.getId());
	        calculTotal(); // Recalcule le total après la suppression
	    }
	    private void calculTotal(){
	        Double total=0.0;
	        for(Paiement p:pObservablelist ) {total+=p.getMontant();}
	        totalValue.setText(total+" DH");
	    }
	    private void getProduits() {
	        PaiementsManagementDao dao = new PaiementsManagementDao();
	        List<Paiement> produits = dao.getAll();
	        pObservablelist.addAll(produits);
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
	       //venteTableView.setMinHeight(500);
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
	          window.setHeight(screenHeight); // Tail
	        window.setTitle("La liste des paiements");
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
	    		
	    		 PdfPaiements pdf=new PdfPaiements ();
	    		 pdf.exportProductsToPdf("PDF_MODEL/modele_pm.pdf", "PDF/paiements.pdf", pObservablelist);
	    		
	    	});
	    	
	    	   dashboardButton.setOnAction(event -> {
	    	       
	    		   	new DashboardPaiementsWindow(pObservablelist);
	    	      

	    	    });
	    
	    }
	    public PaiementsListWindow() {
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

