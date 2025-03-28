package application.paiement;

import java.util.Date;
import java.util.List;

import application.vente.Vente;
import application.vente.VenteManagementDao;

public class Paiement {
	private long id;
    private long cliId;
    private double montant;
    private Date datePaiement;
    private String methodePaiement;
    private String statutPaiement;

    public Paiement(long id, long cliId,Date datePaiement, String methodePaiement, String statutPaiement) {
        this.id = id;
        this.cliId = cliId;
        double m=0.0;
        VenteManagementDao d=new  VenteManagementDao();
        List<Vente> vs=d.getAll();
        for(Vente v:vs) {
        	if(v.getClientId()==cliId) {
        		m+=v.getMontant();
        	}
        }
        this.montant = m;
        this.datePaiement = datePaiement;
        this.methodePaiement = methodePaiement;
        this.statutPaiement = statutPaiement;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCliId() {
        return cliId;
    }

    public void setCliId(long venteId) {
        this.cliId = venteId;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getMethodePaiement() {
        return methodePaiement;
    }

    public void setMethodePaiement(String methodePaiement) {
        this.methodePaiement = methodePaiement;
    }

    public String getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(String statutPaiement) {
        this.statutPaiement = statutPaiement;
    }
}

