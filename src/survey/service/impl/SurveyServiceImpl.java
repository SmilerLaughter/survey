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
	 * ȡ���ʾ�ҳ�桢���⡢����ѡ�� ������
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
	 * ȡ�����⼯�ϵ�ѡ��
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
				if (questionTypeId < 6) {// ��ѡ�� �� ������������
					question.setOptions(optionDao.getOptionsByQuestionId(questionId));
					if (question.getOtherType() == 3) { // ����ʽ������
						question.setOtherSelecOptions(otheroptionDao.getOptionsByQuestionId(questionId));
					}
				} else if (questionTypeId != 6) {// ����ʽ���С���
					question.setMaxtrixCols(maxtriColDao.getOptionsByQuestionId(questionId));
					question.setMaxtrixRows(maxtriRowDao.getOptionsByQuestionId(questionId));

					if (questionTypeId == 9) {// �����С��С������б�
						question.setMaxtrixSelectOptions(maxtriOptionDao.getOptionsByQuestionId(questionId));
					}
				}
			}
		}
	}*/

	/**
	 * �����ʾ�
	 */
	@Override
	public void updateSurvey(Survey survey) {

		surveyDao.update(survey);
	}

	/**
	 * �л�״̬
	 */
	@Override
	public void swichStatus(Integer surveyId) {

		surveyDao.swichStatus(surveyDao.getById(surveyId));
	}

	/**
	 * ɾ���ʾ��Լ���Ӧҳ�棬��Ӧ���� ����Ӧѡ��
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
	 * ����survey�� ��־·��
	 */
	@Override
	public void updateLogoPhotoPath(Survey model) {
		// TODO Auto-generated method stub
		surveyDao.updateLogoPath(model);
	}

	/**
	 * ��ȡsurvey �� ҳ�漯�ϣ����� surveyId ���ڴ�������� ҳ�棬��Ϊ�ڽ����ƶ��͸���ʱ���ײ�Ϊ����survey��ҳ�棬֮�󲻰�����survey
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
	 * ��ȡָ��survey�� ҳ�漯��
	 */
	@Override
	public Survey getPagesbySurveyId(Integer surveyId) {

		Survey survey = surveyDao.getById(surveyId);
		survey.setPages(pageDao.getPagesBySurveyId(surveyId));
		return survey;
	}

	
	/**
	 * ��ȡ���ŵ��ʾ�
	 */
	@Override
	public List<Survey> getOpenSurveys() {

		return surveyDao.getOpenSurveys();
	}

	/**
	 * ��ȡָ��survey����ҳ�������ø�ҳ������� �Լ������Ӧ��ѡ���
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
	 * ��ȡָ����survey�������������ҳ��� ��Сҳ�� �� ���ҳ�������ж��Ƿ�����һҳ ���� ��һҳ
	 */
	@Override
	public Survey getSurveyById(Integer surveyId) {
		Survey survey = surveyDao.getById(surveyId);
		survey.setMinOrder(surveyDao.getMinOrder(surveyId));
		survey.setMaxOrder(surveyDao.getMaxOrder(surveyId));
		return surveyDao.getById(surveyId);
	}

	/**
	 * ��ȡ��ǰҳ�����һҳ�棬���������⼯�� �� �����Ӧ��ѡ��ϣ�����orderNo����ɵ�
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
	 * ��ȡ��ǰҳ�����һҳ�棬���������⼯�� �� �����Ӧ��ѡ��ϣ�����orderNo����ɵ�
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
	 * ���ָ��survey�� �ʾ��
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
	 * ��ȡ��ǰsurvey�� ���⣬Ϊ������׼��
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
	 * Ϊ���⼯������ѡ��
	 */
	public void setOptionsOfQuestions(List<Question > questions){
		if (questions != null && questions.size() > 0) {
			for (Question question : questions) {
				questionService.setOptionsOfQuestion(question);
			}
		}
	}
}
