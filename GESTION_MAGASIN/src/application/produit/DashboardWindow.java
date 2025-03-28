package application.produit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardWindow {
    private Label titleLabel = new Label("Tableau de bord des produits");
    private VBox chartsBox = new VBox();
    private CategoryAxis xAxisPrix = new CategoryAxis();
    private NumberAxis yAxisPrix = new NumberAxis();
    private BarChart<String, Number> barChartPrix = new BarChart<>(xAxisPrix, yAxisPrix);
    private XYChart.Series<String, Number> prixSeries = new XYChart.Series<>();
    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    private PieChart pieChartQte = new PieChart(pieChartData);
    private CategoryAxis xAxisTotal = new CategoryAxis();
    private NumberAxis yAxisTotal = new NumberAxis();
    private LineChart<String, Number> lineChartTotal = new LineChart<>(xAxisTotal, yAxisTotal);
    private XYChart.Series<String, Number> totalSeries = new XYChart.Series<>();
    private Stage window = new Stage();
    private VBox root = new VBox();
    private Scene scene = new Scene(root, 1000, 800); // Taille personnalisée selon vos besoins

    private ObservableList<Produit> produits;

    public DashboardWindow(ObservableList<Produit> produits) {
        this.produits = produits;
        initWindow();
        addNodesToPane();
        addStylesToNodes();
        window.show();
    }

    private void initWindow() {
        window.setTitle("Tableau de bord");
        window.setScene(scene);
    }

    
    private void addNodesToPane() {
        barChartPrix.setTitle("Prix des produits");
        xAxisPrix.setLabel("Produit");
        yAxisPrix.setLabel("Prix (DH)");
        prixSeries.setName("Prix");

        // Graphique à barres pour les prix des produits
        for (Produit produit : produits) {
            prixSeries.getData().add(new XYChart.Data<>(produit.getDesignation(), produit.getPrix()));
        }
        barChartPrix.getData().add(prixSeries);

        // Graphique à secteurs pour la quantité des produits
        pieChartQte.setTitle("Quantité des produits");
        for (Produit produit : produits) {
            PieChart.Data data = new PieChart.Data(produit.getDesignation() + " (" + produit.getQte() + ")", produit.getQte());

            // Calcul du pourcentage
            double percentage = (double) produit.getQte() / getTotalQte() * 100;

            // Création d'un Label pour afficher le pourcentage
            Label label = new Label(String.format("%.1f%%", percentage));
            label.getStyleClass().add("chart-label"); // Ajouter une classe CSS si nécessaire
            label.setUserData(data); // Stockage du PieChart.Data dans le UserData du Label

            // Événement pour mettre à jour le Label lorsque la tranche change
            data.pieValueProperty().addListener((observable, oldValue, newValue) -> {
                label.setText(String.format("%.1f%%", (newValue.doubleValue() / getTotalQte()) * 100));
            });

            // Ajout de l'élément PieChart.Data au PieChart
            pieChartData.add(data);
        }


        // Graphique à lignes pour le total des produits
        lineChartTotal.setTitle("Total des produits");
        xAxisTotal.setLabel("Produit");
        yAxisTotal.setLabel("Total (DH)");

        totalSeries.setName("Total");
        for (Produit produit : produits) {
            totalSeries.getData().add(new XYChart.Data<>(produit.getDesignation(), produit.getTotal()));
        }
        lineChartTotal.getData().add(totalSeries);

        // Ajout des graphiques à la racine VBox
        chartsBox.getChildren().addAll(titleLabel, barChartPrix, pieChartQte, lineChartTotal);
        chartsBox.setSpacing(20);
        chartsBox.setPadding(new Insets(10));
        chartsBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(chartsBox);
    }

    private void addStylesToNodes() {
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        titleLabel.getStyleClass().add("LabelTitle");
        chartsBox.getStyleClass().add("fen");
        barChartPrix.getStyleClass().add("chart");
        pieChartQte.getStyleClass().add("chart");

        // Style personnalisé pour les étiquettes du PieChart
        pieChartQte.setLabelLineLength(10);
        pieChartQte.setLegendVisible(true); // Afficher la légende du PieChart
    }

    private int getTotalQte() {
        int totalQte = 0;
        for (Produit produit : produits) {
            totalQte += produit.getQte();
        }
        return totalQte;
    }
}

