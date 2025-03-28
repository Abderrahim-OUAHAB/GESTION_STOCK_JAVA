package application.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardClientsWindow {
    Label titleLabel = new Label("Tableau de bord des clients");
    VBox chartsBox = new VBox();
    CategoryAxis xAxisPrenom = new CategoryAxis();
    NumberAxis yAxisPrenom = new NumberAxis();
    BarChart<String, Number> barChartPrenom = new BarChart<>(xAxisPrenom, yAxisPrenom);
    XYChart.Series<String, Number> prenomSeries = new XYChart.Series<>();
    CategoryAxis xAxisNom = new CategoryAxis();
    NumberAxis yAxisNom = new NumberAxis();
    BarChart<String, Number> barChartNom = new BarChart<>(xAxisNom, yAxisNom);
    XYChart.Series<String, Number> nomSeries = new XYChart.Series<>();
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    PieChart pieChartTel = new PieChart(pieChartData);
    private Stage window = new Stage();
    private VBox root = new VBox();
    private Scene scene = new Scene(root, 1000, 800); // Taille personnalisée selon vos besoins
    private ObservableList<Client> clients;

    public DashboardClientsWindow(ObservableList<Client> clients) {
        this.clients = clients;
        initWindow();
        addNodesToPane();
        addStylesToNodes();
        window.show();
    }

    private void initWindow() {
        window.setTitle("Tableau de bord des clients");
        window.setScene(scene);
    }

    private void addNodesToPane() {
        barChartPrenom.setTitle("Distribution des clients par prénom");
        xAxisPrenom.setLabel("Prénom");
        yAxisPrenom.setLabel("Nombre de clients");
        prenomSeries.setName("Prénom");

        // Graphique à barres pour la distribution des clients par prénom
        for (Client client : clients) {
            prenomSeries.getData().add(new XYChart.Data<>(client.getFirstname(), 1));
        }
        barChartPrenom.getData().add(prenomSeries);

        // Graphique à barres pour la distribution des clients par nom
        barChartNom.setTitle("Distribution des clients par nom");
        xAxisNom.setLabel("Nom");
        yAxisNom.setLabel("Nombre de clients");
        nomSeries.setName("Nom");

        for (Client client : clients) {
            nomSeries.getData().add(new XYChart.Data<>(client.getLastname(), 1));
        }
        barChartNom.getData().add(nomSeries);

        // Graphique à secteurs pour les téléphones
        for (Client client : clients) {
            PieChart.Data data = new PieChart.Data(client.getTel(), 1);
            pieChartData.add(data);
        }

        pieChartTel.setTitle("Répartition des clients par téléphone");

        // Ajout des graphiques à la racine VBox
        chartsBox.getChildren().addAll(titleLabel, barChartPrenom, barChartNom, pieChartTel);
        chartsBox.setSpacing(20);
        chartsBox.setPadding(new Insets(10));
        chartsBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(chartsBox);
    }

    private void addStylesToNodes() {
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        titleLabel.getStyleClass().add("LabelTitle");
        titleLabel.setAlignment(Pos.CENTER);
        chartsBox.getStyleClass().add("fen");
        barChartPrenom.getStyleClass().add("chart");
        barChartNom.getStyleClass().add("chart");
        pieChartTel.getStyleClass().add("chart");
    }
}
