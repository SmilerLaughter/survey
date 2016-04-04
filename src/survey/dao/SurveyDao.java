package survey.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


import survey.model.Survey;

@Qualifier("surveyDao")
@Repository
public interface SurveyDao extends BaseDao<Survey>{

	public List<Survey> getSurveysByUserId(int id);

	public void swichStatus(Survey survey);

	public void updateLogoPath(Survey model);

	public List<Survey> getOtherSurveys(Survey survey);

	public List<Survey> getOpenSurveys();

	public float getMinOrder(Integer surveyId);

	public float getMaxOrder(Integer surveyId);
}
