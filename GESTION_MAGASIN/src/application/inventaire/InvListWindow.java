package application.inventaire;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import application.produit.ProductManagementDao;
import application.produit.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class InvListWindow {
	   Stage window=new Stage();
	    VBox root=new VBox();
	    Scene scene =new Scene(root);
	    HBox botot=new HBox();
	    Label titleLabel =new Label("Liste des inventaires");
	    HBox totalHbox=new HBox();
	    HBox buttonsBox=new HBox();
	    Label totalLabel=new Label("Total :");
	    Label totalValue=new Label("0");
	    TableColumn<Inventaire,Long> IDcolumn=new TableColumn<>("Id");
	    TableColumn<Inventaire,Long> descolumn=new TableColumn<>("Id Produit");
	    TableColumn<Inventaire,Integer> qtecolumn=new TableColumn<>("Qte");
	    TableColumn<Inventaire,LocalDate> datecolumn=new TableColumn<>("Date d'ajout");
	    TableColumn<Inventaire, Void> actionCol = new TableColumn<>("Actions");
	    TableView<Inventaire> produitTableView=new TableView<>();
	    ObservableList<Inventaire> produitObservablelist=FXCollections.observableArrayList();
	    Button produitPdfBoutton=new Button("Télécharger PDF");
	    Button dashboardButton = new Button("Dashboard");
	    //ajouter des clomns aux table
	    @SuppressWarnings("unchecked")
		private void addColmnTotableView(){
	        produitTableView.getColumns().addAll(IDcolumn,descolumn,qtecolumn,datecolumn,actionCol);
	        produitTableView.setItems(produitObservablelist);
	    }
	    
	    private void updateColumns(){
	        IDcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
	        IDcolumn.setPrefWidth(100);
	        descolumn.setCellValueFactory(new PropertyValueFactory<>("produitId"));
	        descolumn.setPrefWidth(250);
	
	        qtecolumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
	        qtecolumn.setPrefWidth(150);

	        datecolumn.setCellValueFactory(new PropertyValueFactory<>("dateAjout"));
	        datecolumn.setPrefWidth(200);
	        // Ajouter la colonne d'actions
	        actionCol.setCellFactory(new Callback<TableColumn<Inventaire, Void>, TableCell<Inventaire, Void>>() {
	            @Override
	            public TableCell<Inventaire, Void> call(final TableColumn<Inventaire, Void> param) {
	                final TableCell<Inventaire, Void> cell = new TableCell<Inventaire, Void>() {

	                	  Button btnEdit = new Button("Modifier");
	                	  
	                	    Button btnDelete = new Button("Supprimer");
	                	  

	                    {
	                        btnEdit.setOnAction(event -> {
	                        	Inventaire produit = getTableView().getItems().get(getIndex());
	                            handleEditAction(produit);
	                        });

	                        btnDelete.setOnAction(event -> {
	                        	Inventaire produit = getTableView().getItems().get(getIndex());
	                            handleDeleteAction(produit);
	                        });
	                    }

	                    HBox pane = new HBox(btnEdit, btnDelete);
	                    // Ajouter la classe CSS appropriée
	                 
	                    	
	                    
	                

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
	    private void handleEditAction(Inventaire produit) {
	    	EditInventaireWindow ep=	 new EditInventaireWindow(produit);
	    	  ep.showAndWait();
	    	  
	    	    calculTotal();
	    	    produitTableView.refresh();
	    	
	    }
	    

	    private void handleDeleteAction(Inventaire produit) {
	        // Logique pour supprimer le produit
	        produitObservablelist.remove(produit);
	    	 ProductManagementDao d=new ProductManagementDao();
	    	  Produit p=d.getOne(produit.getProduitId());
	    	   d.update(new Produit(p.getId(),p.getDesignation(),p.getPrix(),p.getQte()-produit.getQuantite(),Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())));
	        InvManagementDao dao=new  InvManagementDao ();
	        dao.delete(produit.getId());
	        calculTotal(); // Recalcule le total après la suppression
	    }
	    
	    private void calculTotal(){
	       double total=0;
	       ProductManagementDao dao = new ProductManagementDao();
	      
	        for(Inventaire p:produitObservablelist ) {
	        	for(Produit o:dao.getAll()) {
	        		if(p.getProduitId()==o.getId()) {
	        			total+=o.getPrix()*p.getQuantite();
	        		}
	        	}
	        	
	        	}
	        totalValue.setText(total+" DH");
	    }
	    private void getProduits() {
	    	InvManagementDao  dao = new InvManagementDao ();
	        List<Inventaire> produits = dao.getAll();
	        produitObservablelist.addAll(produits);
	    }
	    private void addStylesToNodes() {
	        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	        titleLabel.getStyleClass().add("LabelTitle");
	        titleLabel.setMinWidth(window.getWidth());
	        totalLabel.getStyleClass().add("LabelTotal");
	        totalValue.getStyleClass().add("LabelTotal");
	        totalHbox.getStyleClass().add("menuButton");
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
	        window.setTitle("La liste des inventaires");
	        window.getIcons().add(new Image("file:logo.png"));
	        window.setScene(scene);

	    }
	    private void addNodesTopane(){
	    	//totalHbox.setMinHeight(10);
	        totalHbox.getChildren().addAll(totalLabel,totalValue);
	        root.getChildren().addAll(titleLabel,produitTableView);
	        buttonsBox.getChildren().addAll(produitPdfBoutton, dashboardButton);
	   

	         HBox spacer = new HBox();
	         HBox.setHgrow(spacer, Priority.ALWAYS);
	         
	         botot.getChildren().addAll(totalHbox, spacer, buttonsBox);
	         botot.setSpacing(10); // Optionnel : ajoute un petit espace entre les éléments et le spacer
	         root.getChildren().add(botot);
	    }
	    
	    
	    private  void addEvents(){
	    	produitPdfBoutton.setOnAction(event->{
	    		
	    		 PdfInv pdf=new   PdfInv();
	    		 pdf.exportProductsToPdf("PDF_MODEL/modele_i.pdf", "PDF/invs.pdf", produitObservablelist);
	    		
	    	});
	 	   dashboardButton.setOnAction(event -> {
    	       
   		   	new DashboardInventaireWindow(produitObservablelist);
   	      

   	    });
	    
	    }
	    
	    public InvListWindow() {
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
