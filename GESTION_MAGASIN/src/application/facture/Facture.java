package application.facture;

import java.util.Date;
import java.util.List;

import application.client.Client;
import application.produit.Produit;
import application.vente.Vente;

public class Facture {
    private long id;
    private Client client;
    private String dateFacture;
    private double total;

    private List<Produit> details;
    private List<Vente> details_v;
    public List<Vente> getDetails_v() {
		return details_v;
	}

	public void setDetails_v(List<Vente> details_v) {
		this.details_v = details_v;
	}

	// Constructeur
    public Facture(long id, Client client, String dateFacture, double total, List<Produit> details, List<Vente> details_v) {
        this.id = id;
        this.client = client;
        this.dateFacture = dateFacture;
        this.total = total;
        this.details = details;
        this.details_v= details_v;
     
    }

    // Getters et Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(String dateFacture) {
        this.dateFacture = dateFacture;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

  

    public List<Produit> getDetails() {
        return details;
    }

    public void setDetails(List<Produit> details) {
        this.details = details;
    }
}
