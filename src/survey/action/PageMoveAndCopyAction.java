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
	
	private Survey survey;//��ҳ������survey
	public Survey getSurvey() {
		return survey;
	}
	
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	
	private List<Survey> otherSurveys ;//���˱�ҳ�������surveys
	public List<Survey> getOtherSurveys() {
		return otherSurveys;
	}
	
	public void setOtherSurveys(List<Survey> otherSurveys) {
		this.otherSurveys = otherSurveys;
	}
	
	/**
	 * ��ȡ��ǰ�û�������survey  ��   ��Ӧ������ҳ�棬����ת���ƶ��͸���ҳ�棬����������ҳ���survey������ǰ��
	 * @return
	 */
	public String toMoveOrCopyPage(){
		otherSurveys = surveyService.getSurveysWithPages(user.getUserId(),model.getSurveyId());
		survey = surveyService.getPagesbySurveyId(model.getSurveyId());
		model = pageService.getById(model.getPageId());
		survey.getPages().remove(model);
		return "moveOrCopyPaage";
	}
	
	private Integer targetPageId;//Ŀ��ҳ��id
	private Integer targetSurveyId;//Ŀ��surveyId�������ж����ƶ����Ǹ���
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
	
	private int pose; //��¼֮ǰ����֮��0Ϊ֮ǰ��1Ϊ֮��
	
	public void setPose(int pose) {
		this.pose = pose;
	}
	
	/**
	 * �ƶ��͸���ҳ���������surveyId��Ⱦ����ƶ�������Ⱦ��Ǹ��ƣ�������ϣ���ת��Ŀ��surveyҳ��
	 * @return
	 */
	public String moveOrCopyPage(){
		if(model.getSurveyId() == targetSurveyId){
			pageService.move(model.getPageId(),targetPageId,pose);
		}else {
			pageService.deepCopy(model.getPageId(),targetPageId,targetSurveyId,pose);
		}
		
		model.setSurveyId(targetSurveyId); //ת��Ŀ��ҳ��
		return "designAction";
	}

}
