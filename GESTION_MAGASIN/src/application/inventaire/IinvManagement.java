package application.inventaire;

import java.util.List;

import application.BD.IDao;

public interface IinvManagement extends IDao<Inventaire>  {
	List<Inventaire> getAll();
	void add(Inventaire p);
	void delete(long id);
	Inventaire getOne(long id);
	List<Inventaire> getAll(String designation);
	void update(Inventaire p);
}
