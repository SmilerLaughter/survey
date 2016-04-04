package survey.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import survey.model.MaxtrixRow;
import survey.model.Option;

@Repository("optionDao")
public interface OptionDao extends BaseOptionDao {
	public List<Option> getOptionsByQuestionId(int id);
}
