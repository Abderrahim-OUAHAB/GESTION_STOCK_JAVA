package application.inventaire;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import application.produit.ProductManagementDao;
import application.produit.Produit;
import javafx.collections.ObservableList;

public class PdfInv {

	static final int max_size=20;
	static int size;
	

	static void openFile(String path) {
		try {
			if ((new File(path)).exists()) {
				//System.out.println("Ouverture du fichier en cours...");
				Process p = Runtime.getRuntime().exec("open " + path);//pour MAC OS
				//Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+path);//POUR WINDOWS
				p.waitFor();

			} else {
				System.err.println("File not found");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public  void exportProductsToPdf(String pathFileModel, String pathFileResult,     ObservableList<Inventaire> products) {
        PdfReader reader = null;
        PdfStamper stamper = null;
        BaseFont bf = null;
        PdfContentByte content = null;

        try {
            reader = new PdfReader(pathFileModel);
            stamper = new PdfStamper(reader, new FileOutputStream(pathFileResult));
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            content = stamper.getOverContent(1);

            int startX1 = 30; // Position de départ de la première colonne
            int startX2 = 145; // Position de départ de la deuxième colonne
            int startY = 680; // Position de départ en hauteur
            int deltaY = 35; // Espacement vertical entre chaque produit

            content.beginText();
            content.setFontAndSize(bf, 10);
           
            double total_global=0.0;
            int i=0;
           for(Inventaire produit :products) {
 
                if (produit != null) {
                	
         	       ProductManagementDao dao = new ProductManagementDao();
         	      
         	      
         	        	for(Produit o:dao.getAll()) {
         	        		if(produit.getProduitId()==o.getId()) {
         	        			total_global+=o.getPrix()*produit.getQuantite();
         	        		}
         	        	
         	        	
         	        	}
                    long ref = produit.getId();
                  Long des = produit.getProduitId();
                    int qte = produit.getQuantite();
                  Date d=produit.getDateAjout();
                    // Positionnement des informations du produit dans les colonnes appropriées
                    content.setTextMatrix(startX1, startY - i * deltaY);
                    content.showText(String.valueOf(ref));
                    content.setTextMatrix(startX2, startY - i * deltaY);
                    content.showText(String.valueOf(des));
                    content.setTextMatrix(startX2 + 200, startY - i * deltaY);
                    content.showText(String.valueOf(qte));
                    content.setTextMatrix(startX2 + 320, startY - i * deltaY);
                    content.showText(String.valueOf(d));
         i++;
                }
            }
          
            total_global = Double.parseDouble(String.format("%.2f", total_global));
          
            content.setTextMatrix( 500,55);
            content.showText(String.valueOf(total_global)+" DH");
            content.endText();
            content.stroke();

            stamper.close();
            reader.close();

            openFile("PDF/invs.pdf");

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (stamper != null) stamper.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
			
				e.printStackTrace();
			}
        }
    }
}
