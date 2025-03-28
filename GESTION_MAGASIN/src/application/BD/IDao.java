package application.BD;

import java.util.List;

public interface IDao <T>{
	List<T> getAll();
	void add(T obj);
	void delete(long id);
	T getOne(long id);
	List<T> getAll(String designation);
	void update(T obj);
}
