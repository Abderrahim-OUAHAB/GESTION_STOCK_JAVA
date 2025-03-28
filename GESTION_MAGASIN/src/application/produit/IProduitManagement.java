package application.produit;

import java.util.List;

import application.BD.IDao;

public interface IProduitManagement extends IDao<Produit> {
	List<Produit> getAll();
	void add(Produit p);
	void delete(long id);
	Produit getOne(long id);
	List<Produit> getAll(String designation);
	void update(Produit p);
}
