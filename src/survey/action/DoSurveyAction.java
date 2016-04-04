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
	private Page page;//当前page
	
	public Page getPage() {
		return page;
	}
	
	public void setPage(Page page) {
		this.page = page;
	}
	
	private List<Survey> surveys;//存储开发的survey
	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}

	public List<Survey> getSurveys() {
		return surveys;
	}

	/**
	 * 先清除session里面的数据，获取开放的调查
	 * @return 跳转到开放的列表
	 */
	public String toListOpenSurveys(){
		
		sessionMap.remove("allParams");
		sessionMap.remove("currentSurvey");
		surveys = surveyService.getOpenSurveys();
		if(surveys == null && surveys.size() == 0){
			addActionError("还没有开放的调查");
		}
		return "listOpenSurveysPage";
	}
	
	/**
	 * 获取调查的首页，并把对应的survey对象放进session中  ，
	 * allParams： 其值用于存放页面问题答案，为了清除的时候不用遍历页面集合，直接 remove allparams
	 * @return  调转至显示问题的页面
	 */
	public String toDoSurveyPage(){
		
		page = surveyService.getFirstPage(model.getSurveyId());
		Survey survey = surveyService.getSurveyById(model.getSurveyId());
		sessionMap.put("currentSurvey", survey);
		sessionMap.put("allParams",  new HashMap<String, Object>());
		return "pageofSurvey";
	}

	private Map<String, Map<String, String[]>> answersMap; //用于存放对应页面的答案，key 对应页面id ，value对应问题的参数集合
	
	/**
	 * 返回当前页面的上一页面，并把当前页面的参数集合放进session中，用于回显
	 * @return
	 */
	public String toPrePage(){
		answersMap = (Map<String,  Map<String, String[] >> ) sessionMap.get("allParams");
		answersMap.put(page.getPageId().toString(), params);
		
		page = surveyService.getPrePage(((Survey)sessionMap.get("currentSurvey")).getSurveyId(),page.getPageId());
		return "pageofSurvey";
	}
	
	/**
	 * 返回当前页面的下一页面，并把当前页面的参数集合放进session中，用于回显
	 * @return
	 */
	public String toNextPage(){
		answersMap = (Map<String,  Map<String, String[]>>) sessionMap.get("allParams");
		answersMap.put(page.getPageId().toString(), params);
		page = surveyService.getNextPage(((Survey)sessionMap.get("currentSurvey")).getSurveyId(),page.getPageId());
		return "pageofSurvey";
	}
	
	
	/**
	 * 判断问题选项是否被选中，
	 * @param questionId  前台传回的问题标识
	 * @param optionId  选项标识
	 * @param selectType 对于checked 或者 selected
	 * @return 返回对应的被选中的类型
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
	 * 对于文本问题的文本值
	 * 
	 */
	public String getText( String questionId){
		answersMap = (Map<String,  Map<String, String[]>>)sessionMap.get("allParams");
		Map<String, String[]> paramsMap =answersMap.get(page.getPageId().toString());
		String string =  Util.getText(paramsMap, questionId);
		return "value =" + string ;
	}
	
	
	/**
	 * 把问卷的答案放进数据库中，并清除session中的survey 和  allParams
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
	private Page page;//当前page
	
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
			addActionError("还没有开放的调查");
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
