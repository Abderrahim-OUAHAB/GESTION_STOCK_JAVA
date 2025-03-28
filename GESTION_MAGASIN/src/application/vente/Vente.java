package application.vente;

import java.util.Date;

import application.produit.ProductManagementDao;
import application.produit.Produit;

public class Vente {
	private long id;
    private long clientId;
    private long produitId;
    private int quantite;
    private double montant;
    private Date dateVente;

    public Vente() {
        // Constructeur par défaut
    }

    public Vente(long id,long clientId, long produitId, int quantite, Date dateVente) {
    	this.id=id;
        this.clientId = clientId;
        this.produitId = produitId;
        this.quantite = quantite;
        ProductManagementDao d=new ProductManagementDao();
        Produit p=d.getOne(produitId);
        if(p!=null) {
        this.montant =quantite*p.getPrix() ;}
        this.dateVente = dateVente;
    }

    // Getters et Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getProduitId() {
        return produitId;
    }

    public void setProduitId(long produitId) {
        this.produitId = produitId;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDateVente() {
        return dateVente;
    }

    public void setDateVente(Date dateVente) {
        this.dateVente = dateVente;
    }
}
