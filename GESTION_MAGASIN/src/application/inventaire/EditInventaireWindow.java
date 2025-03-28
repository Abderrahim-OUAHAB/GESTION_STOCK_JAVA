package application.inventaire;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import application.produit.ProductManagementDao;
import application.produit.Produit;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditInventaireWindow {

    private Stage window;
    private TextField produitIdField, quantiteField, dateAjoutField;
    private Inventaire inventaire;

    public EditInventaireWindow(Inventaire inventaire) {
        this.inventaire = inventaire;
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Modifier l'inventaire");

        // Create and configure GridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Inventaire fields
        Label produitIdLabel = new Label("ID Produit:");
        GridPane.setConstraints(produitIdLabel, 0, 0);
        produitIdField = new TextField(String.valueOf(inventaire.getProduitId()));
        GridPane.setConstraints(produitIdField, 1, 0);

        Label quantiteLabel = new Label("Quantité:");
        GridPane.setConstraints(quantiteLabel, 0, 1);
        quantiteField = new TextField(String.valueOf(inventaire.getQuantite()));
        GridPane.setConstraints(quantiteField, 1, 1);

       // Label dateAjoutLabel = new Label("Date d'Ajout:");
        //GridPane.setConstraints(dateAjoutLabel, 0, 2);
        //dateAjoutField = new TextField(String.valueOf(inventaire.getDateAjout()));
        //GridPane.setConstraints(dateAjoutField, 1, 2);

        // Confirm button
        Button confirmButton = new Button("Confirmer");
        confirmButton.setOnAction(e -> updateInventaire());
        GridPane.setConstraints(confirmButton, 1, 3);

        grid.getChildren().addAll(produitIdLabel, produitIdField, quantiteLabel, quantiteField, confirmButton);

        Scene scene = new Scene(grid);
        window.setScene(scene);
    }

    private void updateInventaire() {
    	 ProductManagementDao d=new ProductManagementDao();
    	  Produit p=d.getOne(Long.parseLong(produitIdField.getText()));
   	   d.update(new Produit(p.getId(),p.getDesignation(),p.getPrix(),p.getQte()+(Integer.parseInt(quantiteField.getText())-inventaire.getQuantite()),Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())));
        inventaire.setProduitId(Long.parseLong(produitIdField.getText()));
        inventaire.setQuantite(Integer.parseInt(quantiteField.getText()));
        inventaire.setDateAjout( convertToDate(LocalDate.now()));
        InvManagementDao dao=new InvManagementDao ();
  	
  
	   dao.update(inventaire);
        window.close();
        
        
    }
    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    public void showAndWait() {
        window.showAndWait();
    }
}
