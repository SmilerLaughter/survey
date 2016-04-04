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
	 * 实现父类 的方法，重新设置 baseDao的值
	 */
	@Autowired
	@Override
	public void setBaseDao(BaseDao<Page> baseDao) {
		// TODO Auto-generated method stub
		baseDao = pageDao;

	}

	/**
	 * 根据 pageId 删除 当前页面 及其 包含的 问题，以及对应问题的所有选项,以及答案里包含的问题
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
	 * 同一调查的页面进行移动，如果目标页面为首页，则移动页面为 目标页面的页序 -0.1，如果目标页面为 尾页，则移动页面的页序 为目标页面页序
	 * +0.1， 如果目标页面在中间，则移动页面的页序取目标页面前或后的平均值
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
	 * 设置或更改页面的页序，若是移动，则是更改移动页面的页序，若是复制，则是设置新页面的页序
	 */

	public void handleOrder(Page targetPage, Page handlePage, int pose) {
		float targetOrder = targetPage.getOrderNo();
		if (pose == 0) {// 目标页面前面
			if (getPrepage(targetPage) == null) {// 如果目标页面为首页
				handlePage.setOrderNo(targetOrder - 0.1f);
			} else {// 目标页在中间
				handlePage.setOrderNo((getPrepage(targetPage).getOrderNo() + targetOrder) / 2);
			}
		} else {// 目标页面后面
			if (getNextPage(targetPage) == null) {// 目标页面为末页
				handlePage.setOrderNo(targetOrder + 0.1f);
			} else {// 目标页在中间
				handlePage.setOrderNo((targetOrder + getNextPage(targetPage).getOrderNo()) / 2);
			}
		}
	}

	/**
	 * 获取页序比目标页面大的页面集合，如果为空，则表示目标页面为尾页，如果不为空，则返回目标页面的后一页
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
	 * 获取页序比目标页面小的页面集合，如果为空，则表示目标页面为首页，不为空，就返回目标页面的前一页
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
	 * 深度复制，并进行数据库插入
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
		// 设置order
		handleOrder(targetPage, copyPage, pose);

		// 插入数据库
		pageDao.add(copyPage);
		pageDao.updatePageOrder(copyPage);
		// 插入问题 和 选项
		int questionId = 0;
		for (Question question : copyPage.getQuestions()) {
			question.setPageId(copyPage.getPageId());//重新设置为新页面的页面id 
			questionDao.add(question);
			questionId = question.getQuestionId();

			if (question.getQuestionTypeId() < 6) {
				updateQuestionIdOfOptions(question.getOptions(), questionId);//所有选项id需要更改 对应的questionId
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
	 * 更改选项的 问题id
	 * @param options 选项集合
	 * @param questionId  更改的questionId
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
