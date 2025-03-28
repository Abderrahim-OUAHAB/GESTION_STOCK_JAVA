package application.inventaire;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardInventaireWindow {
    Label titleLabel = new Label("Tableau de bord des inventaires");
    VBox chartsBox = new VBox();
    CategoryAxis xAxisQuantite = new CategoryAxis();
    NumberAxis yAxisQuantite = new NumberAxis();
    BarChart<String, Number> barChartQuantite = new BarChart<>(xAxisQuantite, yAxisQuantite);
    XYChart.Series<String, Number> quantiteSeries = new XYChart.Series<>();
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    PieChart pieChartProduit = new PieChart(pieChartData);
    CategoryAxis xAxisDate = new CategoryAxis();
    NumberAxis yAxisDate = new NumberAxis();
    LineChart<String, Number> lineChartDate = new LineChart<>(xAxisDate, yAxisDate);
    XYChart.Series<String, Number> dateSeries = new XYChart.Series<>();
    private Stage window = new Stage();
    private VBox root = new VBox();
    private Scene scene = new Scene(root, 1000, 800); // Taille personnalisée selon vos besoins
    private ObservableList<Inventaire> inventaires;

    public DashboardInventaireWindow(ObservableList<Inventaire> inventaires) {
        this.inventaires = inventaires;
        initWindow();
        addNodesToPane();
        addStylesToNodes();
        window.show();
    }

    private void initWindow() {
        window.setTitle("Tableau de bord des inventaires");
        window.setScene(scene);
    }

    private void addNodesToPane() {
        barChartQuantite.setTitle("Quantité d'inventaire par produit");
        xAxisQuantite.setLabel("Produit ID");
        yAxisQuantite.setLabel("Quantité");
        quantiteSeries.setName("Quantité");

        // Graphique à barres pour les quantités d'inventaire
        for (Inventaire inventaire : inventaires) {
            quantiteSeries.getData().add(new XYChart.Data<>(String.valueOf(inventaire.getProduitId()), inventaire.getQuantite()));
        }
        barChartQuantite.getData().add(quantiteSeries);

        // Graphique à secteurs pour les produits
        for (Inventaire inventaire : inventaires) {
            PieChart.Data data = new PieChart.Data(String.valueOf(inventaire.getProduitId()), inventaire.getQuantite());
            pieChartData.add(data);
        }

        pieChartProduit.setTitle("Répartition des produits");

        // Graphique à lignes pour les ajouts d'inventaire au fil du temps
        lineChartDate.setTitle("Ajouts d'inventaire au fil du temps");
        xAxisDate.setLabel("Date");
        yAxisDate.setLabel("Quantité");

        dateSeries.setName("Ajouts d'inventaire");
        for (Inventaire inventaire : inventaires) {
            dateSeries.getData().add(new XYChart.Data<>(inventaire.getDateAjout().toString(), inventaire.getQuantite()));
        }
        lineChartDate.getData().add(dateSeries);

        // Ajout des graphiques à la racine VBox
        chartsBox.getChildren().addAll(titleLabel, barChartQuantite, pieChartProduit, lineChartDate);
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
        barChartQuantite.getStyleClass().add("chart");
        pieChartProduit.getStyleClass().add("chart");
        lineChartDate.getStyleClass().add("chart");
    }
}
