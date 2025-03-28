package application.produit;
import java.time.LocalDate;

import java.time.ZoneId;
import java.util.Date;
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
import javafx.util.Callback;
import javafx.stage.Stage;

public class ProduitsListWindow {

    Stage window=new Stage();
    VBox root=new VBox();
    Scene scene =new Scene(root);
    HBox botot=new HBox();
    Label titleLabel =new Label("Liste des produits");
    HBox totalHbox=new HBox();
    HBox buttonsBox=new HBox();
    Label totalLabel=new Label("Total :");
    Label totalValue=new Label("0.0");
    TableColumn<Produit,Long> IDcolumn=new TableColumn<>("Id");
    TableColumn<Produit,String> descolumn=new TableColumn<>("Designation");
    TableColumn<Produit,Double> prixcolumn=new TableColumn<>("Prix");
    TableColumn<Produit,Integer> qtecolumn=new TableColumn<>("Qte");
    TableColumn<Produit,Double> totalcolumn=new TableColumn<>("Total");
    TableColumn<Produit,LocalDate> datecolumn=new TableColumn<>("Date");
    TableColumn<Produit, Void> actionCol = new TableColumn<>("Actions");
    
    TableView<Produit> produitTableView=new TableView<>();
    ObservableList<Produit> produitObservablelist=FXCollections.observableArrayList();
    Button produitPdfBoutton=new Button("Télécharger PDF");
    Button dashboardButton = new Button("Dashboard");
  
    //ajouter des clomns aux table
    @SuppressWarnings("unchecked")
	private void addColmnTotableView(){
        produitTableView.getColumns().addAll(IDcolumn,descolumn,prixcolumn,qtecolumn,totalcolumn,datecolumn,actionCol);
        produitTableView.setItems(produitObservablelist);
    }
    
    
    private void updateColumns(){
        IDcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        IDcolumn.setPrefWidth(100);
        descolumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        descolumn.setPrefWidth(250);
        prixcolumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        prixcolumn.setPrefWidth(150);
        qtecolumn.setCellValueFactory(new PropertyValueFactory<>("qte"));
        qtecolumn.setPrefWidth(150);
        totalcolumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        totalcolumn.setPrefWidth(150);
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        // Ajouter la colonne d'actions
        actionCol.setCellFactory(new Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>>() {
            @Override
            public TableCell<Produit, Void> call(final TableColumn<Produit, Void> param) {
                final TableCell<Produit, Void> cell = new TableCell<Produit, Void>() {

                	
                	  Button btnEdit = new Button("Modifier");
                	    Button btnDelete = new Button("Supprimer");

                    {
                        btnEdit.setOnAction(event -> {
                            Produit produit = getTableView().getItems().get(getIndex());
                            handleEditAction(produit);
                        });

                        btnDelete.setOnAction(event -> {
                            Produit produit = getTableView().getItems().get(getIndex());
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
    private void handleEditAction(Produit produit) {
    	EditProductWindow ep=	 new EditProductWindow(produit);
    	  ep.showAndWait();
  
    	    calculTotal();
    	    produitTableView.refresh();
    }

    private void handleDeleteAction(Produit produit) {
        // Logique pour supprimer le produit
        produitObservablelist.remove(produit);
        ProductManagementDao dao=new  ProductManagementDao ();
        dao.delete(produit.getId());
        calculTotal(); // Recalcule le total après la suppression
    }
    private void calculTotal(){
        double total=0;
        for(Produit p:produitObservablelist ) {total+=p.getPrix()*p.getQte();}
        totalValue.setText(total+" DH");
    }
    private void getProduits() {
        ProductManagementDao dao = new ProductManagementDao();
        List<Produit> produits = dao.getAll();
        produitObservablelist.addAll(produits);
    }
    private void addStylesToNodes(){
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        titleLabel.getStyleClass().add("LabelTitle");
        titleLabel.setMinWidth(window.getWidth());
        totalLabel.getStyleClass().add("LabelTotal");
        totalValue.getStyleClass().add("LabelTotal");
        totalHbox.getStyleClass().add("menuButton");
        produitTableView.getStyleClass().add("table-row-cell");
        titleLabel.setMinWidth(window.getWidth());
       // produitTableView.setMinHeight(500);
        totalHbox.setSpacing(15);
        produitPdfBoutton.getStyleClass().add("menuButton");
        dashboardButton.getStyleClass().add("menuButton");
  
        root.getStyleClass().add("fen");
        
    }
    private void initWindow(){
    	  // Obtenir la taille de l'écran principal
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        // Définir la taille de la fenêtre en fonction de la taille de l'écran
        window.setWidth(screenWidth ); // Taille de la fenêtre réduite à 80% de la largeur de l'écran
        window.setHeight(screenHeight); // Taille de la fenêtre réduite à 80% de la hauteur de l'écran

        window.setTitle("La liste des produits");
        window.getIcons().add(new Image("file:logo.png"));
        window.setScene(scene);

    }
    private void addNodesTopane(){
    	//totalHbox.setMinHeight(10);
        totalHbox.getChildren().addAll(totalLabel,totalValue);
        root.getChildren().addAll(titleLabel,produitTableView);
        buttonsBox.getChildren().addAll(produitPdfBoutton,dashboardButton);
     
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        botot.getChildren().addAll(totalHbox, spacer, buttonsBox);
        botot.setSpacing(10); // Optionnel : ajoute un petit espace entre les éléments et le spacer
        root.getChildren().add(botot);
    }
    
    private  void addEvents(){
    	produitPdfBoutton.setOnAction(event->{
    		
    		 PdfProduits pdf=new  PdfProduits();
    		 pdf.exportProductsToPdf("PDF_MODEL/modele.pdf", "PDF/produits.pdf", produitObservablelist);
    		
    	});
    	
    	   dashboardButton.setOnAction(event -> {
    	       
    		   	new DashboardWindow(produitObservablelist);
    	      

    	    });
    
    }
    public ProduitsListWindow() {
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

