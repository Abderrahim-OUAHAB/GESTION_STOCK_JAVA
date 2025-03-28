package application.vente;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.paiement.Paiement;
import application.paiement.PaiementsManagementDao;
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

public class EditVenteWindow {

    private Stage window;
    private TextField clientIdField, produitIdField, quantiteField, dateField;
    private Vente vente;

    public EditVenteWindow(Vente vente) {
        this.vente = vente;
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Modifier la vente");

        // Create and configure GridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Vente fields
        Label clientIdLabel = new Label("ID Client:");
        GridPane.setConstraints(clientIdLabel, 0, 0);
        clientIdField = new TextField(String.valueOf(vente.getClientId()));
        GridPane.setConstraints(clientIdField, 1, 0);

        Label produitIdLabel = new Label("ID Produit:");
        GridPane.setConstraints(produitIdLabel, 0, 1);
        produitIdField = new TextField(String.valueOf(vente.getProduitId()));
        GridPane.setConstraints(produitIdField, 1, 1);

        Label quantiteLabel = new Label("Quantité:");
        GridPane.setConstraints(quantiteLabel, 0, 2);
        quantiteField = new TextField(String.valueOf(vente.getQuantite()));
        GridPane.setConstraints(quantiteField, 1, 2);

        //Label montantLabel = new Label("Montant:");
        //GridPane.setConstraints(montantLabel, 0, 3);
        //montantField = new TextField(String.valueOf(vente.getMontant()));
        //GridPane.setConstraints(montantField, 1, 3);

        // Confirm button
        Button confirmButton = new Button("Confirmer");
        confirmButton.setOnAction(e -> confirmEdit());
        GridPane.setConstraints(confirmButton, 1, 4);

        grid.getChildren().addAll(clientIdLabel, clientIdField, produitIdLabel, produitIdField, quantiteLabel, quantiteField, confirmButton);

        Scene scene = new Scene(grid, 300, 250);
        window.setScene(scene);
    }

    private void confirmEdit() {
    	 ProductManagementDao d=new ProductManagementDao();
    	  Produit p=d.getOne(Long.parseLong(produitIdField.getText()));
          d.update(new Produit(p.getId(),p.getDesignation(),p.getPrix(),(p.getQte()+vente.getQuantite())-Integer.parseInt(quantiteField.getText()),convertToDate(LocalDate.now())));
          PaiementsManagementDao da=new  PaiementsManagementDao ();
   	   List<Paiement> pms=new ArrayList<>();
   	   pms=da.getAll();

        // Update the vente with new values
        vente.setClientId(Long.parseLong(clientIdField.getText()));
        vente.setProduitId(Long.parseLong(produitIdField.getText()));
        vente.setQuantite(Integer.parseInt(quantiteField.getText()));
       vente.setMontant(Integer.parseInt(quantiteField.getText())*p.getPrix());
        vente.setDateVente(convertToDate(LocalDate.now()));

        VenteManagementDao dao=new  VenteManagementDao ();
 	   dao.update(vente);
   	   for(Paiement pm:pms) {
   		   if(pm.getCliId()==Long.parseLong(clientIdField.getText())) {
   			
   			   da.update(new Paiement(pm.getId(),pm.getCliId(),Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),pm.getMethodePaiement(),pm.getStatutPaiement()));
   		   }
   	   }
        // Close the window
        window.close();
    }
    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public void showAndWait() {
        window.showAndWait();
    }
}
