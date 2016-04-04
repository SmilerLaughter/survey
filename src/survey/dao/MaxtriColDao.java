package survey.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import survey.model.MaxtrixCol;


@Repository("maxtriColDao")
public interface MaxtriColDao extends BaseOptionDao {
	public List<MaxtrixCol> getOptionsByQuestionId(int id);
}
