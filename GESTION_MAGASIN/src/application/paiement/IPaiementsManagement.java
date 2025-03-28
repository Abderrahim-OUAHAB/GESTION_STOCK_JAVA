package application.paiement;

import java.util.List;

import application.BD.IDao;

public interface IPaiementsManagement extends IDao<Paiement> {
	List<Paiement> getAll();
	void add(Paiement p);
	void delete(long id);
	Paiement getOne(long id);
	List<Paiement> getAll(String designation);
	void update(Paiement p);
}
