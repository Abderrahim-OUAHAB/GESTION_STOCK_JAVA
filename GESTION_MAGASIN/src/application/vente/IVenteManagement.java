package application.vente;

import java.util.List;

import application.BD.IDao;

public interface IVenteManagement extends IDao<Vente>{
	List<Vente> getAll();
	void add(Vente p);
	void delete(long id);
	Vente getOne(long id);
	List<Vente> getAll(String designation);
	void update(Vente p);
}
