package survey.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import survey.model.MaxtriOption;


@Repository("maxtriOptionDao")
public interface MaxtriOptionDao extends BaseOptionDao{
	public List<MaxtriOption> getOptionsByQuestionId(int id);
}
