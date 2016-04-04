package survey.service;

import java.util.List;

import javax.servlet.ServletContext;

import survey.model.Survey;

public interface MonthStatisticService {
	public List<Survey> getMonthsSurvey();
	public void insertWorkBookOfSurveys(List<Survey> monthSurveys,ServletContext servletContext);
	void sendEmails(List<Survey> monthSurveys, ServletContext servletContext);
}