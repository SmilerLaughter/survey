package survey.dao;

import org.springframework.stereotype.Repository;

import survey.model.AnswerOption;

@Repository("answerOptionDao")
public interface AnswerOptionDao  extends BaseAnswerDao<AnswerOption>{

}
