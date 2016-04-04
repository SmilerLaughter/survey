package survey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import survey.dao.BaseDao;

@Service
public interface BaseService<T>  {

	public void add(T t);

	public void delete(Integer id);

	public void update(T t);

	public T getById(Integer id);

	public List<T> getAll();

}
