package survey.service;

import java.util.List;

import org.springframework.stereotype.Service;

import survey.model.Page;
import survey.model.Question;
import survey.model.Survey;

@Service
public interface SurveyService {

	public List<Survey> getSurveysByUserId(int id);
	
	public Survey getSurveyBySurveyId(int id);
	
	public void updateSurvey(Survey survey);
	public void addSurvey(Survey survey);

	public void swichStatus(Integer surveyId);

	public void deleteSurvey(Integer surveyId);

	public void updateLogoPhotoPath(Survey model);

	public List<Survey> getSurveysWithPages(Integer userId, Integer pageId);

	public Survey getPagesbySurveyId(Integer surveyId);

	public List<Survey> getOpenSurveys();

	public Page getFirstPage(Integer surveyId);

	public Survey getSurveyById(Integer surveyId);

	public Page getNextPage(Integer surveyId, Integer pageId);

	public Page getPrePage(Integer surveyId, Integer pageId);

	public void clearAnswersOfSurvey(Integer surveyId);

	public Survey getQuestions(Integer surveyId);

	public void setOptionsOfQuestions(List<Question> questions);
	
}
