package application.client;

import java.util.List;

import application.BD.IDao;

public interface IClientManagement extends IDao<Client> {
	List<Client> getAll();
	void add(Client p);
	void delete(long id);
	Client getOne(long id);
	List<Client> getAll(String designation);
	void update(Client p);
}
