package survey.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import survey.model.MaxtrixRow;

@Repository("maxtriRowDao")
public interface MaxtriRowDao extends BaseOptionDao{
	public List<MaxtrixRow> getOptionsByQuestionId(int id);
}	
