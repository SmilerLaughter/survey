package survey.test;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import survey.service.MonthStatisticService;



public class Test {

	private  ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationCOntext.xml");
	@Autowired
	private MonthStatisticService monthStatisticService = (MonthStatisticService) applicationContext.getBean("monthStatisticService") ;
	@org.junit.Test
	public void test() {
		DataSource dataSource = (DataSource)applicationContext.getBean("dataSource");
		System.out.println(dataSource);
	}
	
	@org.junit.Test
	public void testDate(){
		monthStatisticService.getMonthsSurvey();
	}
	

}
