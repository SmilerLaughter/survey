package survey.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import survey.dao.BaseDao;
import survey.dao.MaxtriColDao;
import survey.dao.MaxtriOptionDao;
import survey.dao.MaxtriRowDao;
import survey.dao.OptionDao;
import survey.dao.OtherOptionDao;
import survey.dao.PageDao;
import survey.dao.QuestionDao;
import survey.model.BaseOption;
import survey.model.MaxtriOption;
import survey.model.MaxtrixCol;
import survey.model.OtherOption;
import survey.model.Page;
import survey.model.Question;
import survey.service.AnswerService;
import survey.service.PageService;
import survey.service.QuestionService;
import survey.service.SurveyService;
import survey.util.DeepCopy;

@Service("pageService")
public class PageServiceImpl extends BaseServiceImpl<Page> implements PageService {

	@Autowired
	private PageDao pageDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private QuestionService questionService;
	@Autowired
	private SurveyService surveyService;
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


	/**
	 * ʵ�ָ��� �ķ������������� baseDao��ֵ
	 */
	@Autowired
	@Override
	public void setBaseDao(BaseDao<Page> baseDao) {
		// TODO Auto-generated method stub
		baseDao = pageDao;

	}

	/**
	 * ���� pageId ɾ�� ��ǰҳ�� ���� ������ ���⣬�Լ���Ӧ���������ѡ��,�Լ��������������
	 */
	@Override
	public void deletePageAndQuestions(Integer pageId) {

		List<Question> questions = questionDao.getQuestionsByPageId(pageId);
		if (questions != null && questions.size() > 0) {
			for (Question question : questions) {
				questionService.deleteQuestion(question.getQuestionId());
			}
		}
		pageDao.delete(pageId);
	}

	/**
	 * ͬһ�����ҳ������ƶ������Ŀ��ҳ��Ϊ��ҳ�����ƶ�ҳ��Ϊ Ŀ��ҳ���ҳ�� -0.1�����Ŀ��ҳ��Ϊ βҳ�����ƶ�ҳ���ҳ�� ΪĿ��ҳ��ҳ��
	 * +0.1�� ���Ŀ��ҳ�����м䣬���ƶ�ҳ���ҳ��ȡĿ��ҳ��ǰ����ƽ��ֵ
	 */
	@Override
	public void move(Integer pageId, Integer targetPageId, int pose) {

		Page targetPage = pageDao.getById(targetPageId);
		Page prePage = new Page();
		prePage.setPageId(pageId);

		handleOrder(targetPage, prePage, pose);
		pageDao.updatePageOrder(prePage);
	}

	/**
	 * ���û����ҳ���ҳ�������ƶ������Ǹ����ƶ�ҳ���ҳ�����Ǹ��ƣ�����������ҳ���ҳ��
	 */

	public void handleOrder(Page targetPage, Page handlePage, int pose) {
		float targetOrder = targetPage.getOrderNo();
		if (pose == 0) {// Ŀ��ҳ��ǰ��
			if (getPrepage(targetPage) == null) {// ���Ŀ��ҳ��Ϊ��ҳ
				handlePage.setOrderNo(targetOrder - 0.1f);
			} else {// Ŀ��ҳ���м�
				handlePage.setOrderNo((getPrepage(targetPage).getOrderNo() + targetOrder) / 2);
			}
		} else {// Ŀ��ҳ�����
			if (getNextPage(targetPage) == null) {// Ŀ��ҳ��Ϊĩҳ
				handlePage.setOrderNo(targetOrder + 0.1f);
			} else {// Ŀ��ҳ���м�
				handlePage.setOrderNo((targetOrder + getNextPage(targetPage).getOrderNo()) / 2);
			}
		}
	}

	/**
	 * ��ȡҳ���Ŀ��ҳ����ҳ�漯�ϣ����Ϊ�գ����ʾĿ��ҳ��Ϊβҳ�������Ϊ�գ��򷵻�Ŀ��ҳ��ĺ�һҳ
	 * 
	 * @param targetPage
	 * @return
	 */
	public Page getNextPage(Page targetPage) {

		List<Page> pages = pageDao.getNextPages(targetPage);
		if (pages != null && pages.size() > 0) {
			return pages.get(0);
		}
		return null;
	}

	/**
	 * ��ȡҳ���Ŀ��ҳ��С��ҳ�漯�ϣ����Ϊ�գ����ʾĿ��ҳ��Ϊ��ҳ����Ϊ�գ��ͷ���Ŀ��ҳ���ǰһҳ
	 * 
	 * @param targetPage
	 * @return
	 */
	public Page getPrepage(Page targetPage) {

		List<Page> pages = pageDao.getPrePages(targetPage);
		if (pages != null && pages.size() > 0) {
			return pages.get(0);
		}
		return null;
	}

	/**
	 * ��ȸ��ƣ����������ݿ����
	 */
	@Override
	public void deepCopy(Integer pageId, Integer targetPageId, Integer targetSurveyId, int pose) {
		// TODO Auto-generated method stub
		Page prePage = pageDao.getById(pageId);
		prePage.setQuestions(questionDao.getQuestionsByPageId(pageId));
		Page targetPage = pageDao.getById(targetPageId);

		surveyService.setOptionsOfQuestions(prePage.getQuestions());
		Page copyPage = (Page) DeepCopy.deepCopy(prePage);
		copyPage.setSurveyId(targetSurveyId);
		// ����order
		handleOrder(targetPage, copyPage, pose);

		// �������ݿ�
		pageDao.add(copyPage);
		pageDao.updatePageOrder(copyPage);
		// �������� �� ѡ��
		int questionId = 0;
		for (Question question : copyPage.getQuestions()) {
			question.setPageId(copyPage.getPageId());//��������Ϊ��ҳ���ҳ��id 
			questionDao.add(question);
			questionId = question.getQuestionId();

			if (question.getQuestionTypeId() < 6) {
				updateQuestionIdOfOptions(question.getOptions(), questionId);//����ѡ��id��Ҫ���� ��Ӧ��questionId
				optionDao.addByBach(question.getOptions());

			} else if (question.getQuestionTypeId() > 6) {
				updateQuestionIdOfOptions(question.getMaxtrixCols(), questionId);
				updateQuestionIdOfOptions(question.getMaxtrixRows(), questionId);

				maxtriColDao.addByBach(question.getMaxtrixCols());
				maxtriRowDao.addByBach(question.getMaxtrixRows());

				if (question.getQuestionTypeId() == 9) {
					updateQuestionIdOfOptions(question.getMaxtrixSelectOptions(), questionId);
					maxtriOptionDao.addByBach(question.getMaxtrixSelectOptions());
				}
			}

			if (question.getOtherType() == 3) {
				updateQuestionIdOfOptions(question.getOptions(), questionId);
				otheroptionDao.addByBach(question.getOtherSelecOptions());
			}
		}

		System.out.println(copyPage);

	}

	/**
	 * ����ѡ��� ����id
	 * @param options ѡ���
	 * @param questionId  ���ĵ�questionId
	 */
	public void updateQuestionIdOfOptions(List<? extends BaseOption> options, int questionId) {
		if (options != null && options.size() > 0) {
			for (BaseOption baseOption : options) {
				baseOption.setQuestionId(questionId);

			}
		}
	}
	
	public void add(Page page){
		pageDao.add(page);
		page.setOrderNo(page.getPageId());
		pageDao.updatePageOrder(page);
	}

}
