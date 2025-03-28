package application.vente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardVentesWindow {
    Label titleLabel = new Label("Tableau de bord des ventes");
    VBox chartsBox = new VBox();
    CategoryAxis xAxisMontant = new CategoryAxis();
    NumberAxis yAxisMontant = new NumberAxis();
    BarChart<String, Number> barChartMontant = new BarChart<>(xAxisMontant, yAxisMontant);
    XYChart.Series<String, Number> montantSeries = new XYChart.Series<>();
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    PieChart pieChartQte = new PieChart(pieChartData);
    CategoryAxis xAxisDate = new CategoryAxis();
    NumberAxis yAxisDate = new NumberAxis();
    LineChart<String, Number> lineChartDate = new LineChart<>(xAxisDate, yAxisDate);
    XYChart.Series<String, Number> dateSeries = new XYChart.Series<>();
    private Stage window = new Stage();
    private VBox root = new VBox();
    private Scene scene = new Scene(root, 1000, 800); // Taille personnalisée selon vos besoins
    private ObservableList<Vente> ventes;

    public DashboardVentesWindow(ObservableList<Vente> ventes) {
        this.ventes = ventes;
        initWindow();
        addNodesToPane();
        addStylesToNodes();
        window.show();
    }

    private void initWindow() {
        window.setTitle("Tableau de bord des ventes");
        window.setScene(scene);
    }

    private void addNodesToPane() {
        barChartMontant.setTitle("Montant des ventes");
        xAxisMontant.setLabel("ID Produit");
        yAxisMontant.setLabel("Montant (DH)");
        montantSeries.setName("Montant");

        // Graphique à barres pour les montants des ventes
        for (Vente vente : ventes) {
            montantSeries.getData().add(new XYChart.Data<>(vente.getProduitId() + "", vente.getMontant()));
        }
        barChartMontant.getData().add(montantSeries);

        // Graphique à secteurs pour la quantité des produits vendus
        for (Vente vente : ventes) {
            double percentage = (double) vente.getQuantite() / getTotalQte() * 100;
            PieChart.Data data = new PieChart.Data(vente.getProduitId() + " (" + vente.getQuantite() + ")", vente.getQuantite());
         
            // Personnalisation de l'étiquette pour inclure le pourcentage
            Label label = new Label(String.format("%.1f%%", percentage));
            label.getStyleClass().add("chart-label"); // Ajouter une classe CSS spécifique

            // Créer un StackPane pour contenir l'étiquette et l'ajouter
           // StackPane stackPane = new StackPane();
            //stackPane.getChildren().addAll(data.getNode(), label);
            pieChartData.add(data);
        }

        pieChartQte.setTitle("Quantité des produits vendus");

        // Graphique à lignes pour les ventes au fil du temps
        lineChartDate.setTitle("Ventes au fil du temps");
        xAxisDate.setLabel("Date");
        yAxisDate.setLabel("Montant (DH)");

        dateSeries.setName("Ventes");
        for (Vente vente : ventes) {
            dateSeries.getData().add(new XYChart.Data<>(vente.getDateVente().toString(), vente.getMontant()));
        }
        lineChartDate.getData().add(dateSeries);

        // Ajout des graphiques à la racine VBox
        chartsBox.getChildren().addAll(titleLabel, barChartMontant, pieChartQte, lineChartDate);
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
        barChartMontant.getStyleClass().add("chart");
        pieChartQte.getStyleClass().add("chart");
        lineChartDate.getStyleClass().add("chart");
    }

    private int getTotalQte() {
        int totalQte = 0;
        for (Vente vente : ventes) {
            totalQte += vente.getQuantite();
        }
        return totalQte;
    }
}
