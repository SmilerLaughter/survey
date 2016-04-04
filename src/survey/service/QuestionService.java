package survey.service;

import org.springframework.stereotype.Service;

import survey.model.Question;

@Service
public interface QuestionService  {

	void addQuestionNotMaxtri(Question model);
	void addQuestionIsMaxtri(Question model);
	void add(Question model);
	Question getById(Integer id);
	void deleteQuestion(Integer id);
	Question getEditQuestion(Integer questionId);
	void updateQuestionNotMaxtri(Question model);
	void updateQuestionIsMaxtri(Question model);
	Question getQuestionWithOptions(Integer questionId);
	void setOptionsOfQuestion(Question question);
}
