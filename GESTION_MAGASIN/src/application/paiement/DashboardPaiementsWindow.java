package application.paiement;

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

public class DashboardPaiementsWindow {
    Label titleLabel = new Label("Tableau de bord des paiements");
    VBox chartsBox = new VBox();
    CategoryAxis xAxisMontant = new CategoryAxis();
    NumberAxis yAxisMontant = new NumberAxis();
    BarChart<String, Number> barChartMontant = new BarChart<>(xAxisMontant, yAxisMontant);
    XYChart.Series<String, Number> montantSeries = new XYChart.Series<>();
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    PieChart pieChartMethode = new PieChart(pieChartData);
    CategoryAxis xAxisDate = new CategoryAxis();
    NumberAxis yAxisDate = new NumberAxis();
    LineChart<String, Number> lineChartDate = new LineChart<>(xAxisDate, yAxisDate);
    XYChart.Series<String, Number> dateSeries = new XYChart.Series<>();
    private Stage window = new Stage();
    private VBox root = new VBox();
    private Scene scene = new Scene(root, 1000, 800); // Taille personnalisée selon vos besoins
    private ObservableList<Paiement> paiements;

    public DashboardPaiementsWindow(ObservableList<Paiement> paiements) {
        this.paiements = paiements;
        initWindow();
        addNodesToPane();
        addStylesToNodes();
        window.show();
    }

    private void initWindow() {
        window.setTitle("Tableau de bord des paiements");
        window.setScene(scene);
    }

    private void addNodesToPane() {
        barChartMontant.setTitle("Montant des paiements");
        xAxisMontant.setLabel("Client ID");
        yAxisMontant.setLabel("Montant (DH)");
        montantSeries.setName("Montant");

        // Graphique à barres pour les montants des paiements
        for (Paiement paiement : paiements) {
            montantSeries.getData().add(new XYChart.Data<>(String.valueOf(paiement.getCliId()), paiement.getMontant()));
        }
        barChartMontant.getData().add(montantSeries);

        // Graphique à secteurs pour les méthodes de paiement
        for (Paiement paiement : paiements) {
            double percentage = 1.0; // Placeholder for calculating percentage if needed
            PieChart.Data data = new PieChart.Data(paiement.getMethodePaiement(), paiement.getMontant());
            
            // Personnalisation de l'étiquette pour inclure le pourcentage
            Label label = new Label(String.format("%.1f%%", percentage));
            label.getStyleClass().add("chart-label"); // Ajouter une classe CSS spécifique

            // Créer un StackPane pour contenir l'étiquette et l'ajouter
            //StackPane stackPane = new StackPane();
            //stackPane.getChildren().addAll(data.getNode(), label);
            pieChartData.add(data);
        }

        pieChartMethode.setTitle("Méthodes de paiement");

        // Graphique à lignes pour les paiements au fil du temps
        lineChartDate.setTitle("Paiements au fil du temps");
        xAxisDate.setLabel("Date");
        yAxisDate.setLabel("Montant (DH)");

        dateSeries.setName("Paiements");
        for (Paiement paiement : paiements) {
            dateSeries.getData().add(new XYChart.Data<>(paiement.getDatePaiement().toString(), paiement.getMontant()));
        }
        lineChartDate.getData().add(dateSeries);

        // Ajout des graphiques à la racine VBox
        chartsBox.getChildren().addAll(titleLabel, barChartMontant, pieChartMethode, lineChartDate);
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
        pieChartMethode.getStyleClass().add("chart");
        lineChartDate.getStyleClass().add("chart");
    }
}
