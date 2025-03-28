package application.facture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import application.produit.Produit;
import application.vente.Vente;
import javafx.collections.ObservableList;

public class PdfFacture {

	static final int max_size=20;
	static int size;
	

	static void openFile(String path) {
		try {
			if ((new File(path)).exists()) {
				System.out.println("Ouverture du fichier en cours...");
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
	
	public  void exportProductsToPdf(String pathFileModel, String pathFileResult,     Facture products) {
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
            int startX2 = 100; // Position de départ de la deuxième colonne
            int startY = 680; // Position de départ en hauteur
            int deltaY = 35; // Espacement vertical entre chaque produit

            content.beginText();
            content.setFontAndSize(bf, 10);
           
            content.setTextMatrix(55, 825);
            content.showText(String.valueOf(products.getId()));
            content.setTextMatrix(55, 780);
            content.showText(String.valueOf(products.getClient().getFirstname()+" "+products.getClient().getLastname()));
            content.setTextMatrix(400, 780);
            content.showText(String.valueOf(products.getDateFacture()));
           
        
            double total_global=0.0;
            int i=0;
        
                
              for(Vente v :products.getDetails_v()) {
            	  String des=null ;
            	  int qte=0;
            	  double prix=0.0;
            	   for(Produit produit :products.getDetails()) {
            		   
                         if(v.getProduitId()==produit.getId()) {
                 	des = produit.getDesignation();
                        		
                        		  
                        	  }
                   
                         qte=v.getQuantite();
                		  prix=v.getMontant(); 
                          
              }
              
                    // Positionnement des informations du produit dans les colonnes appropriées
                   
                    content.setTextMatrix(startX2, startY - i * deltaY);
                    content.showText(des);
                    content.setTextMatrix(startX2 + 250, startY - i * deltaY);
                    content.showText(String.valueOf(qte));
                    content.setTextMatrix(startX2 + 350, startY - i * deltaY);
                    content.showText(String.valueOf(prix));
                  
                    total_global+=prix;
         i++;
                
            }
          double tva=0.2;
 
            total_global = Double.parseDouble(String.format("%.2f", total_global));
            double total_global_avec_taxe=total_global*tva+total_global;
            content.setTextMatrix( 410,87);
            content.showText(String.valueOf(total_global)+" DH");
            content.setTextMatrix( 410,55);
            content.showText(String.valueOf(tva));
            content.setTextMatrix( 410,30);
            content.showText(String.valueOf(total_global_avec_taxe)+" DH");
            content.endText();
            content.stroke();

            stamper.close();
            reader.close();

            openFile("PDF/facture.pdf");

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
