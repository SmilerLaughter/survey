package survey.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import survey.dao.BaseDao;
import survey.service.BaseService;


@Service
@Qualifier("baseService")
public abstract class BaseServiceImpl<T> implements BaseService<T>{

	@Autowired
	protected BaseDao<T> baseDao;
	
	
	/**
	 * 其子类需要实现此方法，设置 baseDao的值，从而调用对应  dao 的方法
	 * @param baseDao
	 */
	public abstract void setBaseDao(BaseDao<T> baseDao) ;
	
	public void add(T t) {
		baseDao.add(t);
	}

	public void delete(Integer id) {
		baseDao.delete(id);
	}

	public void update(T t) {
		baseDao.update(t);
	}

	@Override
	public T getById(Integer id) {
		// TODO Auto-generated method stub
		return baseDao.getById(id);
	}

	@Override
	public List<T> getAll() {
		// TODO Auto-generated method stub
		return baseDao.getAll();
	}

}
