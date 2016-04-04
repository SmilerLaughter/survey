package survey.service;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import survey.model.Question;
import survey.model.Survey;

@Service("answerService")
public interface AnswerService {


	void handleAnswers(Map<String, Map<String, String[]>> answersMap, Survey currentSurvey);
	public void clearAnswersOfQuestions(List<Question> questions);
}
