module GESTION_MAGASIN {
	requires javafx.controls;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.swing;
	requires javafx.base;
	requires javafx.fxml;
	
	requires javafx.media;
	requires javafx.web;
	requires javafx.swt;
	requires itextpdf;
	exports application;

	opens application.produit to javafx.graphics, javafx.fxml,javafx.base,javafx.controls,javafx.swing,javafx.media,javafx.web, javafx.swt;
	opens application.client to javafx.graphics, javafx.fxml,javafx.base,javafx.controls,javafx.swing,javafx.media,javafx.web, javafx.swt;
	opens application.vente to javafx.graphics, javafx.fxml,javafx.base,javafx.controls,javafx.swing,javafx.media,javafx.web, javafx.swt;
	opens application.inventaire to javafx.graphics, javafx.fxml,javafx.base,javafx.controls,javafx.swing,javafx.media,javafx.web, javafx.swt;
	opens application.paiement to javafx.graphics, javafx.fxml,javafx.base,javafx.controls,javafx.swing,javafx.media,javafx.web, javafx.swt;
	opens application.facture to javafx.graphics, javafx.fxml,javafx.base,javafx.controls,javafx.swing,javafx.media,javafx.web, javafx.swt;
	opens application.BD to javafx.graphics, javafx.fxml,javafx.base,javafx.controls,javafx.swing,javafx.media,javafx.web, javafx.swt;
}