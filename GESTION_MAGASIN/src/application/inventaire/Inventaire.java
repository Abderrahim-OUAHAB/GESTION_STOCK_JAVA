package application.inventaire;

import java.util.Date;

public class Inventaire {
    private long id;
    private long produitId;
    private int quantite;
    private Date dateAjout;

    // Constructeur par défaut
    public Inventaire() {
    }

    // Constructeur avec paramètres
    public Inventaire(long id, long produitId, int quantite,Date dateAjout) {
        this.id = id;
        this.produitId = produitId;
        this.quantite = quantite;
        this.dateAjout = dateAjout;
    }

    // Getter et Setter pour id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Getter et Setter pour produitId
    public long getProduitId() {
        return produitId;
    }

    public void setProduitId(long produitId) {
        this.produitId = produitId;
    }

    // Getter et Setter pour quantite
    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    // Getter et Setter pour dateAjout
    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    @Override
    public String toString() {
        return "Inventaire{" +
                "id=" + id +
                ", produitId=" + produitId +
                ", quantite=" + quantite +
                ", dateAjout=" + dateAjout +
                '}';
    }
}
