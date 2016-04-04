package survey.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import survey.dao.MaxtriColDao;
import survey.dao.MaxtriOptionDao;
import survey.dao.MaxtriRowDao;
import survey.dao.OptionDao;
import survey.dao.OtherOptionDao;
import survey.dao.PageDao;
import survey.dao.QuestionDao;
import survey.dao.SurveyDao;
import survey.model.Page;
import survey.model.Question;
import survey.model.Survey;
import survey.service.AnswerService;
import survey.service.PageService;
import survey.service.QuestionService;
import survey.service.SurveyService;

@Qualifier("surveyService")
@Service
public class SurveyServiceImpl implements SurveyService {

	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private SurveyDao surveyDao;
	@Autowired
	private PageDao pageDao;

	@Autowired
	private OtherOptionDao otheroptionDao;
	@Autowired
	private OptionDao optionDao;
	@Autowired
	private MaxtriColDao maxtriColDao;
	@Autowired
	private MaxtriRowDao maxtriRowDao;
	@Autowired
	private MaxtriOptionDao maxtriOptionDao;

	@Autowired
	private PageService pageService;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private QuestionService questionService;

	@Override
	public List<Survey> getSurveysByUserId(int id) {
		return surveyDao.getSurveysByUserId(id);
	}

	public void addSurvey(Survey survey) {

		survey.setCreateTime(new Date());
		surveyDao.add(survey);

		Page page = new Page();
		page.setSurveyId(survey.getSurveyId());
		pageDao.add(page);
		page.setOrderNo(page.getPageId());
		pageDao.updatePageOrder(page);
	}

	
	/**
	 * 取出问卷：页面、问题、问题选项 和其他
	 * 
	 */
	public Survey getSurveyBySurveyId(int id) {
		Survey survey = surveyDao.getById(id);
		List<Page> pages = pageDao.getPagesBySurveyId(id);
		survey.setPages(pages);
		List<Question> questions;
		for (Page page : pages) {
			questions = questionDao.getQuestionsByPageId(page.getPageId());
			page.setQuestions(questions);
			setOptionsOfQuestions(questions);
		}
		return survey;
	}

	/**
	 * 取出问题集合的选项
	 * 
	 * @param questions
	 */
/*	public void setOptionsOfQuestions(List<Question> questions) {
		int questionId = 0;
		int questionTypeId = 0;
		if (questions != null && questions.size() > 0) {

			for (Question question : questions) {
				questionId = question.getQuestionId();
				questionTypeId = question.getQuestionTypeId();
				if (questionTypeId < 6) {// 有选项 或 可能有其他项
					question.setOptions(optionDao.getOptionsByQuestionId(questionId));
					if (question.getOtherType() == 3) { // 下拉式其它项
						question.setOtherSelecOptions(otheroptionDao.getOptionsByQuestionId(questionId));
					}
				} else if (questionTypeId != 6) {// 矩阵式：行、列
					question.setMaxtrixCols(maxtriColDao.getOptionsByQuestionId(questionId));
					question.setMaxtrixRows(maxtriRowDao.getOptionsByQuestionId(questionId));

					if (questionTypeId == 9) {// 矩阵：行、列、下拉列表
						question.setMaxtrixSelectOptions(maxtriOptionDao.getOptionsByQuestionId(questionId));
					}
				}
			}
		}
	}*/

	/**
	 * 更新问卷
	 */
	@Override
	public void updateSurvey(Survey survey) {

		surveyDao.update(survey);
	}

	/**
	 * 切换状态
	 */
	@Override
	public void swichStatus(Integer surveyId) {

		surveyDao.swichStatus(surveyDao.getById(surveyId));
	}

	/**
	 * 删除问卷，以及对应页面，对应问题 ，对应选项
	 */
	@Override
	public void deleteSurvey(Integer surveyId) {

		List<Page> pages = pageDao.getPagesBySurveyId(surveyId);
		if (pages != null && pages.size() > 0) {
			for (Page page : pages) {
				pageService.deletePageAndQuestions(page.getPageId());
			}
		}
		surveyDao.delete(surveyId);
	}

	
	/**
	 * 更改survey的 标志路径
	 */
	@Override
	public void updateLogoPhotoPath(Survey model) {
		// TODO Auto-generated method stub
		surveyDao.updateLogoPath(model);
	}

	/**
	 * 获取survey 的 页面集合，除开 surveyId 等于传入参数的 页面，因为在进行移动和复制时，首部为操作survey的页面，之后不包含改survey
	 */
	@Override
	public List<Survey> getSurveysWithPages(Integer userId, Integer surveyId) {

		Survey survey = new Survey();
		survey.setSurveyId(surveyId);
		survey.setUserId(userId);
		List<Survey> surveys = surveyDao.getOtherSurveys(survey);

		if (surveys != null && surveys.size() > 0) {
			for (Survey survey2 : surveys) {
				if (survey2.getSurveyId() != surveyId) {
					survey2.setPages(pageDao.getPagesBySurveyId(survey2.getSurveyId()));
				}
			}
		}
		return surveys;
	}

	/**
	 * 获取指定survey的 页面集合
	 */
	@Override
	public Survey getPagesbySurveyId(Integer surveyId) {

		Survey survey = surveyDao.getById(surveyId);
		survey.setPages(pageDao.getPagesBySurveyId(surveyId));
		return survey;
	}

	
	/**
	 * 获取开放的问卷
	 */
	@Override
	public List<Survey> getOpenSurveys() {

		return surveyDao.getOpenSurveys();
	}

	/**
	 * 获取指定survey的首页，并设置该页面的问题 以及问题对应的选项集合
	 */
	@Override
	public Page getFirstPage(Integer surveyId) {

		List<Page> pages = pageDao.getPagesBySurveyId(surveyId);
		Page page = null;
		if (pages != null & pages.size() > 0) {
			page = pages.get(0);
			List<Question> questions = questionDao.getQuestionsByPageId(page.getPageId());
			setOptionsOfQuestions(questions);
			page.setQuestions(questions);
		}
		return page;
	}

	/**
	 * 获取指定的survey，并设置其包含页面的 最小页序 和 最大页序，用于判断是否有下一页 或者 上一页
	 */
	@Override
	public Survey getSurveyById(Integer surveyId) {
		Survey survey = surveyDao.getById(surveyId);
		survey.setMinOrder(surveyDao.getMinOrder(surveyId));
		survey.setMaxOrder(surveyDao.getMaxOrder(surveyId));
		return surveyDao.getById(surveyId);
	}

	/**
	 * 获取当前页面的下一页面，并设置问题集合 和 问题对应的选项集合，根据orderNo排序可得
	 */
	@Override
	public Page getNextPage(Integer surveyId, Integer pageId) {

		Page page = new Page();
		page.setSurveyId(surveyId);
		page.setPageId(pageId);
		
		List<Page> pages = pageDao.getNextPagesByOrderNoAndPageId(page);
		if(pages != null && pages.size() > 0){
			page = pages.get(0);
			List<Question> questions = questionDao.getQuestionsByPageId(page.getPageId());
			setOptionsOfQuestions(questions);
			page.setQuestions(questions);
		}
		
		return page;
	}

	/**
	 * 获取当前页面的上一页面，并设置问题集合 和 问题对应的选项集合，根据orderNo排序可得
	 */
	@Override
	public Page getPrePage(Integer surveyId, Integer pageId) {

		Page page = new Page();
		page.setPageId(pageId);
		page.setSurveyId(surveyId);
		
		List<Page> pages = pageDao.getPrePagesByOrderNoAndPageId(page);
		page = pages.get(0);
		List<Question> questions = questionDao.getQuestionsByPageId(page.getPageId());
		setOptionsOfQuestions(questions);
		page.setQuestions(questions);
		return page;
	}

	/**
	 * 清除指定survey的 问卷答案
	 */
	@Override
	public void clearAnswersOfSurvey(Integer surveyId) {

		List<Page> pages = pageDao.getPagesBySurveyId(surveyId);
		List<Question> questions;
		if (pages != null && pages.size() > 0) {
			for (Page page : pages) {
				questions = questionDao.getQuestionsByPageId(page.getPageId());
				answerService.clearAnswersOfQuestions(questions);
			}
		}
	}

	/**
	 * 获取当前survey的 问题，为分析做准备
	 */
	@Override
	public Survey getQuestions(Integer surveyId) {
		Survey survey = surveyDao.getById(surveyId);
		List<Page> pages = pageDao.getPagesBySurveyId(surveyId);
		survey.setPages(pages);
		List<Question> questions;
		for (Page page : pages) {
			questions = questionDao.getQuestionsByPageId(page.getPageId());
			page.setQuestions(questions);
		}
		return survey;
	}
	
	/**
	 * 为问题集合设置选项
	 */
	public void setOptionsOfQuestions(List<Question > questions){
		if (questions != null && questions.size() > 0) {
			for (Question question : questions) {
				questionService.setOptionsOfQuestion(question);
			}
		}
	}
}
