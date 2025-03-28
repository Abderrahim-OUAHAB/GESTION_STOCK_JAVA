package application.produit;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditProductWindow {

    private Stage window;
    private TextField designationField, prixField, qteField;
    private DatePicker datePicker;
    private Produit produit;

    public EditProductWindow(Produit produit) {
        this.produit = produit;
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Modifier le produit");

        // Create and configure GridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Product fields
        Label designationLabel = new Label("Designation:");
        GridPane.setConstraints(designationLabel, 0, 0);
        designationField = new TextField(produit.getDesignation());
        GridPane.setConstraints(designationField, 1, 0);

        Label prixLabel = new Label("Prix:");
        GridPane.setConstraints(prixLabel, 0, 1);
        prixField = new TextField(String.valueOf(produit.getPrix()));
        GridPane.setConstraints(prixField, 1, 1);

        Label qteLabel = new Label("Quantité:");
        GridPane.setConstraints(qteLabel, 0, 2);
        qteField = new TextField(String.valueOf(produit.getQte()));
        GridPane.setConstraints(qteField, 1, 2);

        //Label dateLabel = new Label("Date:");
        //GridPane.setConstraints(dateLabel, 0, 3);
        //datePicker = new DatePicker(convertToLocalDate(produit.getDate()));
       // GridPane.setConstraints(datePicker, 1, 3);

        // Confirm button
        Button confirmButton = new Button("Confirmer");
        confirmButton.setOnAction(e -> confirmEdit());
        GridPane.setConstraints(confirmButton, 1, 4);

        grid.getChildren().addAll(designationLabel, designationField, prixLabel, prixField, qteLabel, qteField, confirmButton);

        Scene scene = new Scene(grid, 300, 250);
        window.setScene(scene);
    }

    private void confirmEdit() {
        // Update the product with new values
        produit.setDesignation(designationField.getText());
        produit.setPrix(Double.parseDouble(prixField.getText()));
        produit.setQte(Integer.parseInt(qteField.getText()));
        produit.setDate(convertToDate(LocalDate.now()));
        produit.setTotal(Double.parseDouble(prixField.getText())* Integer.parseInt(qteField.getText()));
   	   ProductManagementDao dao=new  ProductManagementDao ();
	   dao.update(produit);
        // Close the window
        window.close();
    }

    public void showAndWait() {
        window.showAndWait();
    }


    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
