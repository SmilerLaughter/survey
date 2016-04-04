package survey.quartz;



import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import survey.model.Survey;
import survey.service.MonthStatisticService;

public class MonthStatistic implements ServletContextAware {

	private MonthStatisticService monthStatisticService;
	
	
	public MonthStatisticService getMonthStatisticService() {
		return monthStatisticService;
	}


	public void setMonthStatisticService(MonthStatisticService monthStatisticService) {
		this.monthStatisticService = monthStatisticService;
	}

/**
 * ÈÎÎñ
 */
	public void statistic(){
		List<Survey> monthSurveys = monthStatisticService.getMonthsSurvey();
		if (monthSurveys != null && monthSurveys.size() > 0) {
			monthStatisticService.insertWorkBookOfSurveys(monthSurveys, servletContext);
			monthStatisticService.sendEmails(monthSurveys,servletContext);
		}
	}


	private ServletContext servletContext;
	@Override
	public void setServletContext(ServletContext arg0) {

		servletContext = arg0;
	}
}
