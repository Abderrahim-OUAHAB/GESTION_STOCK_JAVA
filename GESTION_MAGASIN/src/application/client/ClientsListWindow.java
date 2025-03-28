package application.client;

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

public class ClientsListWindow {
	  Stage window=new Stage();
	    VBox root=new VBox();
	    Scene scene =new Scene(root);
	    HBox botot=new HBox();
	    Label titleLabel =new Label("Liste des clients");
	    HBox totalHbox=new HBox();
	    HBox buttonsBox=new HBox();
	    Label totalLabel=new Label("Total des clients:");
	    Label totalValue=new Label("0");
	    TableColumn<Client,Long> IDcolumn=new TableColumn<>("Id");
	    TableColumn<Client,String> prenomcolumn=new TableColumn<>("Prenom");
	    TableColumn<Client,String> nomcolumn=new TableColumn<>("Nom");
	    TableColumn<Client,String> telcolumn=new TableColumn<>("Téléphone");
	    TableColumn<Client, Void> actionCol = new TableColumn<>("Actions");
	    TableView<Client> clientTableView=new TableView<>();
	    ObservableList<Client> clientObservablelist=FXCollections.observableArrayList();
	    Button produitPdfBoutton=new Button("Télécharger PDF");
	    Button dashboardButton = new Button("Dashboard");
	    //ajouter des clomns aux table
	    @SuppressWarnings("unchecked")
		private void addColmnTotableView(){
	    	clientTableView.getColumns().addAll(IDcolumn,prenomcolumn,nomcolumn,telcolumn,actionCol);
	        clientTableView.setItems(clientObservablelist);
	    }
	    
	    private void updateColumns(){
	        IDcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
	        IDcolumn.setPrefWidth(100);
	        prenomcolumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
	        prenomcolumn.setPrefWidth(250);
	        nomcolumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
	        nomcolumn.setPrefWidth(150);
	        telcolumn.setCellValueFactory(new PropertyValueFactory<>("tel"));
	        telcolumn.setPrefWidth(150);

	        // Ajouter la colonne d'actions
	        actionCol.setCellFactory(new Callback<TableColumn<Client, Void>, TableCell<Client, Void>>() {
	            @Override
	            public TableCell<Client, Void> call(final TableColumn<Client, Void> param) {
	                final TableCell<Client, Void> cell = new TableCell<Client, Void>() {
	                    private final Button btnEdit = new Button("Modifier");
	                    private final Button btnDelete = new Button("Supprimer");

	                    {
	                        btnEdit.setOnAction(event -> {
	                            Client client = getTableView().getItems().get(getIndex());
	                            handleEditAction(client);
	                        });

	                        btnDelete.setOnAction(event -> {
	                            Client client = getTableView().getItems().get(getIndex());
	                            handleDeleteAction(client);
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
	    private void handleEditAction(Client client) {
	        new EditClientWindow(client).showAndWait();
	        ClientManagementDao dao =new ClientManagementDao();
	        dao.update(client);
	        clientTableView.refresh(); // Refresh the table view to reflect changes
	        calculTotal(); 
	    }

	    private void handleDeleteAction(Client client) {
	        clientObservablelist.remove(client);
	        ClientManagementDao dao =new ClientManagementDao();
	        dao.delete(client.getId());
	        calculTotal(); // Recalcule le total après la suppression
	    }
	    private void calculTotal(){
	        int total=0;
	        for(Client p:clientObservablelist ) {total+=1;}
	        totalValue.setText(total+"");
	    }
	    private void getProduits() {
	        ClientManagementDao dao = new ClientManagementDao();
	        List<Client> cs = dao.getAll();
	        clientObservablelist.addAll(cs);
	    }
	    private void addStylesToNodes(){
	        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	        titleLabel.getStyleClass().add("LabelTitle");
	        titleLabel.setMinWidth(window.getWidth());
	        totalLabel.getStyleClass().add("LabelTotal");
	        totalValue.getStyleClass().add("LabelTotal");
	        totalHbox.getStyleClass().add("menuButton");
	        clientTableView.getStyleClass().add("table-row-cell");
	        titleLabel.setMinWidth(window.getWidth());
	        //clientTableView.setMinHeight(500);
	        totalHbox.setSpacing(15);
	        produitPdfBoutton.getStyleClass().add("menuButton");
	        dashboardButton.getStyleClass().add("menuButton");
	        root.getStyleClass().add("fen");
	    }
	    private void initWindow(){
	    	 Screen screen = Screen.getPrimary();
	         double screenWidth = screen.getBounds().getWidth();
	         double screenHeight = screen.getBounds().getHeight();
	         window.setWidth(screenWidth ); // Taille de la fenêtre réduite à 80% de la largeur de l'écran
	         window.setHeight(screenHeight);
	        window.setTitle("La liste des clients");
	        window.getIcons().add(new Image("file:logo.png"));
	        window.setScene(scene);

	    }
	    private void addNodesTopane(){
	    	totalHbox.getChildren().addAll(totalLabel,totalValue);
	         root.getChildren().addAll(titleLabel,clientTableView);
	         buttonsBox.getChildren().addAll(produitPdfBoutton,dashboardButton);
	     
	         HBox spacer = new HBox();
	         HBox.setHgrow(spacer, Priority.ALWAYS);
	         
	         botot.getChildren().addAll(totalHbox, spacer, buttonsBox);
	         botot.setSpacing(10); // Optionnel : ajoute un petit espace entre les éléments et le spacer
	         root.getChildren().add(botot);
	    }
	    
	    private  void addEvents(){
	    	produitPdfBoutton.setOnAction(event->{
	    		
	    		 PdfClients pdf=new  PdfClients();
	    		 pdf.exportProductsToPdf("PDF_MODEL/modele_c.pdf", "PDF/clients.pdf", clientObservablelist);
	    		
	    	});
	 	   dashboardButton.setOnAction(event -> {
    	       
   		   	new DashboardClientsWindow(clientObservablelist);
   	      

   	    });
	    }
	    public ClientsListWindow() {
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

