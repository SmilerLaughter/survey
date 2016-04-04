package survey.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import survey.model.BaseAnswer;

@Repository
public interface BaseAnswerDao<T> {

	public void deleteByQuestionId(Integer id);
	public void addByBach(List< ? extends BaseAnswer> list);
	public long getCount(T t);
}
