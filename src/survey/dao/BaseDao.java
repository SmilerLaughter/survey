package survey.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("baseDao")
public interface BaseDao<T> {

	public void add(T t);
	public void delete(Integer id);
	public void update(T t);
	public T getById(Integer id);
	public List<T> getAll();
}
