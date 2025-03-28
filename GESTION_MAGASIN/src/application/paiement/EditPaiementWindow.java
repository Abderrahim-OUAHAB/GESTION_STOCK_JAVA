package application.paiement;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import application.vente.Vente;
import application.vente.VenteManagementDao;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditPaiementWindow {

    private Stage window;
    private TextField clientIdField, montantField, datePaiementField, methodePaiementField, statutPaiementField;
    private Paiement paiement;

    public EditPaiementWindow(Paiement paiement) {
        this.paiement = paiement;
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Modifier le paiement");

        // Create and configure GridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Paiement fields
        Label clientIdLabel = new Label("ID Client:");
        GridPane.setConstraints(clientIdLabel, 0, 0);
        clientIdField = new TextField(String.valueOf(paiement.getCliId()));
        GridPane.setConstraints(clientIdField, 1, 0);

        //Label montantLabel = new Label("Montant:");
        //GridPane.setConstraints(montantLabel, 0, 1);
        //montantField = new TextField(String.valueOf(paiement.getMontant()));
        //GridPane.setConstraints(montantField, 1, 1);

       // Label datePaiementLabel = new Label("Date de Paiement:");
        //GridPane.setConstraints(datePaiementLabel, 0, 2);
        //datePaiementField = new TextField(String.valueOf(paiement.getDatePaiement()));
        //GridPane.setConstraints(datePaiementField, 1, 2);

        Label methodePaiementLabel = new Label("Méthode de Paiement:");
        GridPane.setConstraints(methodePaiementLabel, 0, 2);
        methodePaiementField = new TextField(paiement.getMethodePaiement());
        GridPane.setConstraints(methodePaiementField, 1, 2);

        Label statutPaiementLabel = new Label("Statut de Paiement:");
        GridPane.setConstraints(statutPaiementLabel, 0, 3);
        statutPaiementField = new TextField(paiement.getStatutPaiement());
        GridPane.setConstraints(statutPaiementField, 1, 3);

        // Confirm button
        Button confirmButton = new Button("Confirmer");
        confirmButton.setOnAction(e -> confirmEdit());
        GridPane.setConstraints(confirmButton, 1, 5);

        grid.getChildren().addAll(clientIdLabel, clientIdField,  methodePaiementLabel, methodePaiementField, statutPaiementLabel, statutPaiementField, confirmButton);

        Scene scene = new Scene(grid, 300, 250);
        window.setScene(scene);
    }

    private void confirmEdit() {
        // Update the paiement with new values
        paiement.setCliId(Long.parseLong(clientIdField.getText()));
    
        paiement.setDatePaiement(convertToDate(LocalDate.now()));
        paiement.setMethodePaiement(methodePaiementField.getText());
        paiement.setStatutPaiement(statutPaiementField.getText());
        double m=0.0;
        VenteManagementDao d=new  VenteManagementDao();
        List<Vente> vs=d.getAll();
        for(Vente v:vs) {
        	if(v.getClientId()==Long.parseLong(clientIdField.getText())) {
        		m+=v.getMontant();
        	}
        }
        paiement.setMontant(m);
        // Save the updated paiement to the database
     
        PaiementsManagementDao dao=new  PaiementsManagementDao ();
 	   dao.update(paiement);
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
