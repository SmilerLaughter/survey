package survey.dao;

import java.util.List;

import survey.model.BaseOption;

public interface BaseOptionDao {

	public void addByBach(List<? extends BaseOption> list);
	public void deleteOptionsByQuestionId(Integer question);
}
