package application.client;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditClientWindow {

    private Stage window;
    private TextField firstnameField, lastnameField, telField;
    private Client client;

    public EditClientWindow(Client client) {
        this.client = client;
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Modifier le client");

        // Create and configure GridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Client fields
        Label firstnameLabel = new Label("Prénom:");
        GridPane.setConstraints(firstnameLabel, 0, 0);
        firstnameField = new TextField(client.getFirstname());
        GridPane.setConstraints(firstnameField, 1, 0);

        Label lastnameLabel = new Label("Nom:");
        GridPane.setConstraints(lastnameLabel, 0, 1);
        lastnameField = new TextField(client.getLastname());
        GridPane.setConstraints(lastnameField, 1, 1);

        Label telLabel = new Label("Téléphone:");
        GridPane.setConstraints(telLabel, 0, 2);
        telField = new TextField(client.getTel());
        GridPane.setConstraints(telField, 1, 2);

        // Confirm button
        Button confirmButton = new Button("Confirmer");
        confirmButton.setOnAction(e -> confirmEdit());
        GridPane.setConstraints(confirmButton, 1, 3);

        grid.getChildren().addAll(firstnameLabel, firstnameField, lastnameLabel, lastnameField, telLabel, telField, confirmButton);

        Scene scene = new Scene(grid, 300, 200);
        window.setScene(scene);
    }

    private void confirmEdit() {
        // Update the client with new values
        client.setFirstname(firstnameField.getText());
        client.setLastname(lastnameField.getText());
        client.setTel(telField.getText());
        ClientManagementDao dao =new ClientManagementDao();
        dao.update(client);
        // Close the window
        window.close();
    }

    public void showAndWait() {
        window.showAndWait();
    }
}
