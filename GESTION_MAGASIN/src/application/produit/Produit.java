package application.produit;

import java.util.Date;

public class Produit {
    private double total;
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    private Date date;
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    private int qte;
    public int getQte() {
        return qte;
    }
    public void setQte(int qte) {
        this.qte = qte;
    }
    private long id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    private String designation;
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    private double prix;
    public Produit(long id, String designation, double prix,int qte ,Date date) {
        this.id= id;
        this.designation = designation;
        this.prix = prix;
        this.date=date;
        this.qte=qte;
        this.total=this.prix*this.qte;
    }
  
    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }
    @Override
    public String toString() {
        return id+"\t"+designation+"\t"+qte+"\t"+prix+"\t"+date;
    }


}
