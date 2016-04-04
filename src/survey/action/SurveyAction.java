package survey.action;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import survey.interceptor.UserAware;
import survey.model.Survey;
import survey.model.User;
import survey.service.SurveyService;

@Scope("prototype")
@Controller
public class SurveyAction extends BaseAction<Survey> implements UserAware{

	@Autowired
	private SurveyService surveyService;
	
	private User user ; //����У���û��Ƿ��Ѿ���¼
	@Override
	public void setUser(User user) {
		this.user = user;
	}
	
	private List<Survey> surveys;//�û����鼯��
	public List<Survey> getSurveys() {
		return surveys;
	}
	

	
	/**
	 * �����û�id ���õ����鼯��
	 * @return ��ת���ʾ��б�ҳ��
	 */
	public String toListPage(){
		surveys = surveyService.getSurveysByUserId(user.getUserId());
		return "toListPage";
	}
	
	/**
	 * �����ʾ��״̬
	 * @return ��ת���ʾ��б�ҳ��
	 */
	public String swichStatus(){
		
		surveyService.swichStatus(model.getSurveyId());
		return "listPageAction";
	}
	
	
	/**
	 * �����ʾ������ݿ��л�ȡ�������ʾ� �� Ĭ�ϵ�һ��ҳ�� 
	 * @return  ��ת�� ���ҳ��
	 */
	public String createSurvey(){

		model.setUserId(user.getUserId());
		surveyService.addSurvey(model);
		model = surveyService.getSurveyBySurveyId(model.getSurveyId());
		return "create";
	}
	

	/**
	 * ��ȡ��Ӧ�� �ʾ�
	 * @return ��ת�����ҳ��
	 */
	public String design(){
		
		model = surveyService.getSurveyBySurveyId(model.getSurveyId());
		return "design";
	}
	
	/**
	 * �����ʾ�����ԣ���ת�� �����ʾ�Ķ�Ӧ��
	 */
	public String toUpdate(){
		
		model = surveyService.getSurveyBySurveyId(model.getSurveyId());
		return "updatePage";
	}
	
	/**
	 * �����ʾ�
	 * @return ��ת�����ҳ��
	 */
	public String update(){
		surveyService.updateSurvey(model);
		return "designAction";
	}
	
	
	/**
	 * ɾ���ʾ��Լ� �ʾ��°���������ҳ�棬�Լ���Ӧҳ���µ����� ���⣬�Լ���Ӧ���������ѡ��
	 * @return
	 */
	public String deleteSurvey(){
	
		surveyService.deleteSurvey(model.getSurveyId());
		return "listPageAction";		
	}

	
	/**
	 * �������Ĵ�
	 * @return
	 */
	public String clearAnswersOfSurvey(){
		
		surveyService.clearAnswersOfSurvey(model.getSurveyId());
		return "listPageAction";
	}
	
	/**
	 * ��ȡ��ǰsurvey�����⼯��Ϊͳ����׼��
	 * @return
	 */
	public String toListQuestionsOfSurvey(){
		
		model = surveyService.getQuestions(model.getSurveyId());
		return "listQuestionsPage";
	}
			

}
