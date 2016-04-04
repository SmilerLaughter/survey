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
	
	private User user ; //用于校验用户是否已经登录
	@Override
	public void setUser(User user) {
		this.user = user;
	}
	
	private List<Survey> surveys;//用户调查集合
	public List<Survey> getSurveys() {
		return surveys;
	}
	

	
	/**
	 * 根据用户id ，得到调查集合
	 * @return 调转至问卷列表页面
	 */
	public String toListPage(){
		surveys = surveyService.getSurveysByUserId(user.getUserId());
		return "toListPage";
	}
	
	/**
	 * 更改问卷的状态
	 * @return 调转至问卷列表页面
	 */
	public String swichStatus(){
		
		surveyService.swichStatus(model.getSurveyId());
		return "listPageAction";
	}
	
	
	/**
	 * 新增问卷，从数据库中获取新增的问卷 和 默认的一个页面 
	 * @return  调转至 设计页面
	 */
	public String createSurvey(){

		model.setUserId(user.getUserId());
		surveyService.addSurvey(model);
		model = surveyService.getSurveyBySurveyId(model.getSurveyId());
		return "create";
	}
	

	/**
	 * 获取对应的 问卷
	 * @return 调转至设计页面
	 */
	public String design(){
		
		model = surveyService.getSurveyBySurveyId(model.getSurveyId());
		return "design";
	}
	
	/**
	 * 更改问卷的属性，调转至 更改问卷的对应表单
	 */
	public String toUpdate(){
		
		model = surveyService.getSurveyBySurveyId(model.getSurveyId());
		return "updatePage";
	}
	
	/**
	 * 更改问卷
	 * @return 调转至设计页面
	 */
	public String update(){
		surveyService.updateSurvey(model);
		return "designAction";
	}
	
	
	/**
	 * 删除问卷，以及 问卷下包含的所有页面，以及对应页面下的所有 问题，以及对应问题的所有选项
	 * @return
	 */
	public String deleteSurvey(){
	
		surveyService.deleteSurvey(model.getSurveyId());
		return "listPageAction";		
	}

	
	/**
	 * 清除调查的答案
	 * @return
	 */
	public String clearAnswersOfSurvey(){
		
		surveyService.clearAnswersOfSurvey(model.getSurveyId());
		return "listPageAction";
	}
	
	/**
	 * 获取当前survey的问题集，为统计做准备
	 * @return
	 */
	public String toListQuestionsOfSurvey(){
		
		model = surveyService.getQuestions(model.getSurveyId());
		return "listQuestionsPage";
	}
			

}
