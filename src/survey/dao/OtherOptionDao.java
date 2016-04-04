package survey.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import survey.model.OtherOption;

@Repository("otherOptionDao")
public interface OtherOptionDao extends BaseOptionDao{
	public List<OtherOption> getOptionsByQuestionId(int id);
}
