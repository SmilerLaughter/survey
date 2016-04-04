package survey.action;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import survey.interceptor.UserAware;
import survey.model.Page;
import survey.model.Survey;
import survey.model.User;
import survey.service.PageService;
import survey.service.SurveyService;

@Scope("prototype")
@Controller
public class PageMoveAndCopyAction extends BaseAction<Page> implements UserAware{
	@Autowired
	private PageService pageService;
	@Autowired
	private SurveyService surveyService;
	private User user;
	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		this.user = user;
	}
	
	private Survey survey;//本页面所在survey
	public Survey getSurvey() {
		return survey;
	}
	
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	
	private List<Survey> otherSurveys ;//除了本页面的所有surveys
	public List<Survey> getOtherSurveys() {
		return otherSurveys;
	}
	
	public void setOtherSurveys(List<Survey> otherSurveys) {
		this.otherSurveys = otherSurveys;
	}
	
	/**
	 * 获取当前用户的所有survey  和   对应的所有页面，并跳转到移动和复制页面，包含被操作页面的survey放在最前面
	 * @return
	 */
	public String toMoveOrCopyPage(){
		otherSurveys = surveyService.getSurveysWithPages(user.getUserId(),model.getSurveyId());
		survey = surveyService.getPagesbySurveyId(model.getSurveyId());
		model = pageService.getById(model.getPageId());
		survey.getPages().remove(model);
		return "moveOrCopyPaage";
	}
	
	private Integer targetPageId;//目标页面id
	private Integer targetSurveyId;//目标surveyId，用于判断是移动还是复制
	public void setTargetPageId(Integer targetPageId) {
		this.targetPageId = targetPageId;
	}
	
	public void setTargetSurveyId(Integer targetSurveyId) {
		this.targetSurveyId = targetSurveyId;
	}

	public Integer getTargetPageId() {
		return targetPageId;
	}
	
	public Integer getTargetSurveyId() {
		return targetSurveyId;
	}
	
	private int pose; //记录之前还是之后，0为之前，1为之后
	
	public void setPose(int pose) {
		this.pose = pose;
	}
	
	/**
	 * 移动和复制页面操作，若surveyId相等就是移动，不相等就是复制，操作完毕，跳转至目标survey页面
	 * @return
	 */
	public String moveOrCopyPage(){
		if(model.getSurveyId() == targetSurveyId){
			pageService.move(model.getPageId(),targetPageId,pose);
		}else {
			pageService.deepCopy(model.getPageId(),targetPageId,targetSurveyId,pose);
		}
		
		model.setSurveyId(targetSurveyId); //转至目标页面
		return "designAction";
	}

}
