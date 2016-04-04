package survey.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import survey.model.MaxtriOption;
import survey.model.MaxtrixCol;
import survey.model.MaxtrixRow;
import survey.model.Option;
import survey.model.OtherOption;
import survey.model.Question;

@Repository
@Qualifier("questionDao")
public interface QuestionDao extends BaseDao<Question> {

	public List<Question> getQuestionsByPageId(int id);

	public void deleteQuestionAndOptions(Question question);
	
}
