package survey.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import survey.model.Page;
import survey.model.Survey;
import survey.service.AnswerService;
import survey.service.SurveyService;
import survey.util.Util;

@Scope("prototype")
@Controller
public class DoSurveyAction extends BaseAction<Survey> implements SessionAware,ParameterAware{

	@Autowired 
	private SurveyService surveyService;
	@Autowired
	private AnswerService answerService;
	private Page page;//��ǰpage
	
	public Page getPage() {
		return page;
	}
	
	public void setPage(Page page) {
		this.page = page;
	}
	
	private List<Survey> surveys;//�洢������survey
	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}

	public List<Survey> getSurveys() {
		return surveys;
	}

	/**
	 * �����session��������ݣ���ȡ���ŵĵ���
	 * @return ��ת�����ŵ��б�
	 */
	public String toListOpenSurveys(){
		
		sessionMap.remove("allParams");
		sessionMap.remove("currentSurvey");
		surveys = surveyService.getOpenSurveys();
		if(surveys == null && surveys.size() == 0){
			addActionError("��û�п��ŵĵ���");
		}
		return "listOpenSurveysPage";
	}
	
	/**
	 * ��ȡ�������ҳ�����Ѷ�Ӧ��survey����Ž�session��  ��
	 * allParams�� ��ֵ���ڴ��ҳ������𰸣�Ϊ�������ʱ���ñ���ҳ�漯�ϣ�ֱ�� remove allparams
	 * @return  ��ת����ʾ�����ҳ��
	 */
	public String toDoSurveyPage(){
		
		page = surveyService.getFirstPage(model.getSurveyId());
		Survey survey = surveyService.getSurveyById(model.getSurveyId());
		sessionMap.put("currentSurvey", survey);
		sessionMap.put("allParams",  new HashMap<String, Object>());
		return "pageofSurvey";
	}

	private Map<String, Map<String, String[]>> answersMap; //���ڴ�Ŷ�Ӧҳ��Ĵ𰸣�key ��Ӧҳ��id ��value��Ӧ����Ĳ�������
	
	/**
	 * ���ص�ǰҳ�����һҳ�棬���ѵ�ǰҳ��Ĳ������ϷŽ�session�У����ڻ���
	 * @return
	 */
	public String toPrePage(){
		answersMap = (Map<String,  Map<String, String[] >> ) sessionMap.get("allParams");
		answersMap.put(page.getPageId().toString(), params);
		
		page = surveyService.getPrePage(((Survey)sessionMap.get("currentSurvey")).getSurveyId(),page.getPageId());
		return "pageofSurvey";
	}
	
	/**
	 * ���ص�ǰҳ�����һҳ�棬���ѵ�ǰҳ��Ĳ������ϷŽ�session�У����ڻ���
	 * @return
	 */
	public String toNextPage(){
		answersMap = (Map<String,  Map<String, String[]>>) sessionMap.get("allParams");
		answersMap.put(page.getPageId().toString(), params);
		page = surveyService.getNextPage(((Survey)sessionMap.get("currentSurvey")).getSurveyId(),page.getPageId());
		return "pageofSurvey";
	}
	
	
	/**
	 * �ж�����ѡ���Ƿ�ѡ�У�
	 * @param questionId  ǰ̨���ص������ʶ
	 * @param optionId  ѡ���ʶ
	 * @param selectType ����checked ���� selected
	 * @return ���ض�Ӧ�ı�ѡ�е�����
	 */
	public String isSelect(String questionId,String optionId,String selectType ){
		
		answersMap = (Map<String,  Map<String, String[]>>)sessionMap.get("allParams");
		Map<String, String[]> paramsMap =answersMap.get(page.getPageId().toString());
		if(Util.isContains(paramsMap, questionId, optionId)){
			return selectType;
		}
		return "";
	}
	
	
	/**
	 * �����ı�������ı�ֵ
	 * 
	 */
	public String getText( String questionId){
		answersMap = (Map<String,  Map<String, String[]>>)sessionMap.get("allParams");
		Map<String, String[]> paramsMap =answersMap.get(page.getPageId().toString());
		String string =  Util.getText(paramsMap, questionId);
		return "value =" + string ;
	}
	
	
	/**
	 * ���ʾ�Ĵ𰸷Ž����ݿ��У������session�е�survey ��  allParams
	 * @return
	 */
	public String commitSurvey(){
		answersMap = (Map<String,  Map<String, String[]>>)sessionMap.get("allParams");
		answersMap.put(page.getPageId().toString(), params);
		
		Survey currentSurvey =  (Survey)sessionMap.get("currentSurvey");
		answerService.handleAnswers(answersMap,currentSurvey);
		
		sessionMap.remove("allParams");
		sessionMap.remove("currentSurvey");
		return "returnToListAction";
	}
	
	
	private Map<String, Object> sessionMap;
	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		sessionMap = arg0;
	}

	private Map<String , String[]> params;
	
	@Override
	public void setParameters(Map<String, String[]> arg0) {
			
		params = arg0;
	}
}



/*
public class DoSurveyAction extends BaseAction<Survey> implements SessionAware,ParameterAware{

	@Autowired 
	private SurveyService surveyService;
	private Page page;//��ǰpage
	
	public Page getPage() {
		return page;
	}
	
	public void setPage(Page page) {
		this.page = page;
	}
	
	private List<Survey> surveys;
	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}

	public List<Survey> getSurveys() {
		return surveys;
	}

	public String toListOpenSurveys(){
	
		surveys = surveyService.getOpenSurveys();
		if(surveys == null && surveys.size() == 0){
			addActionError("��û�п��ŵĵ���");
		}
		return "listOpenSurveysPage";
	}
	
	public String toDoSurveyPage(){
		
		page = surveyService.getFirstPage(model.getSurveyId());
		Survey survey = surveyService.getSurveyById(model.getSurveyId());
		sessionMap.put("currentSurvey", survey);
		return "pageofSurvey";
	}

	public String toPrePage(){

		sessionMap.put(page.getPageId().toString(), params);
		page = surveyService.getPrePage(((Survey)sessionMap.get("currentSurvey")).getSurveyId(),page.getPageId());
		return "pageofSurvey";
	}
	
	public String toNextPage(){
		sessionMap.put(page.getPageId().toString(), params);
		page = surveyService.getNextPage(((Survey)sessionMap.get("currentSurvey")).getSurveyId(),page.getPageId());
		return "pageofSurvey";
	}
	
	public String isSelect(String questionId,String optionId,String selectType ){
		
		Map<String, String[]> paramsMap = (Map<String, String[]>)sessionMap.get(page.getPageId().toString());
		if(Util.isContains(paramsMap, questionId, optionId)){
			
			return selectType;
		}
		return "";
	}
	
	public String getText( String questionId){
		Map<String, String[]> paramsMap = (Map<String, String[]>)sessionMap.get(page.getPageId().toString());
		String string =  Util.getText(paramsMap, questionId);
		System.out.println(string);
		return "value =" + string ;
	}
	
	
	private Map<String, Object> sessionMap;
	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		sessionMap = arg0;
	}

	private Map<String , String[]> params;
	
	@Override
	public void setParameters(Map<String, String[]> arg0) {
			
		params = arg0;
	}
}
*/
