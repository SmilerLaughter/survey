package survey.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import survey.dao.AnswerMaxtrixDao;
import survey.dao.AnswerOptionDao;
import survey.dao.AnswerTextDao;
import survey.dao.MaxtriColDao;
import survey.dao.MaxtriRowDao;
import survey.dao.PageDao;
import survey.dao.QuestionDao;
import survey.model.AnswerMaxtrix;
import survey.model.AnswerOption;
import survey.model.AnswerText;
import survey.model.MaxtrixCol;
import survey.model.MaxtrixRow;
import survey.model.Page;
import survey.model.Question;
import survey.model.Survey;
import survey.service.AnswerService;

@Service("answerService")
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private PageDao pageDao;
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private MaxtriRowDao maxtriRowDao;
	@Autowired
	private MaxtriColDao maxtriColDao;
	@Autowired
	private AnswerOptionDao answerOptionDao;
	@Autowired
	private AnswerMaxtrixDao answerMaxtrixDao;
	@Autowired
	private AnswerTextDao answerTextDao;

	/**
	 * 处理问卷的答案，以uuId标识不同的回答者
	 */
	@Override
	public void handleAnswers(Map<String, Map<String, String[]>> answersMap, Survey currentSurvey) {

		List<Page> pages = pageDao.getPagesBySurveyId(currentSurvey.getSurveyId());
		List<Question> questions = null;
		List<AnswerMaxtrix> answerMaxtrixs = new ArrayList<>();
		List<AnswerOption> answerOptions = new ArrayList<>();
		List<AnswerText> answerTexts = new ArrayList<>();
		Map<String, String[]> params;
		String uuId = UUID.randomUUID().toString();

		AnswerText answerText = null;

		int questionType = 0;
		int questionId = 0;

		if (pages != null && pages.size() > 0) {
			for (Page page : pages) {
				params = answersMap.get(page.getPageId().toString());
				if (params != null && params.size() > 0) {

					questions = questionDao.getQuestionsByPageId(page.getPageId());
					if (questions != null && questions.size() > 0) {
						for (Question question : questions) {
							questionId = question.getQuestionId();
							questionType = question.getQuestionTypeId();

							if (questionType < 6) { // 选择 + 其他项
								List<AnswerOption> answerOptions2 = getAnswerOptionsOfQuestion(questionId, params, uuId,
										answerTexts);
								if (answerOptions2 != null && answerOptions2.size() > 0) {
									answerOptions.addAll(answerOptions2);
								}

							} else if (questionType == 6) {// 文本
								answerText = getAnswerText(questionId, params, uuId);
								if (answerText != null) {
									answerTexts.add(answerText);
								}
							} else {// 矩阵式
								List<MaxtrixRow> maxtrixRows = maxtriRowDao.getOptionsByQuestionId(questionId);
								List<AnswerMaxtrix> answerMaxtrixs2;
								if (questionType != 9) {
									if (maxtrixRows != null && maxtrixRows.size() > 0) {
										for (MaxtrixRow maxtrixRow : maxtrixRows) {
											answerMaxtrixs2 = getAnserMaxtrix(params,
													questionId + "_" + maxtrixRow.getRowId(), uuId);
											if (answerMaxtrixs2 != null && answerMaxtrixs2.size() > 0) {
												answerMaxtrixs.addAll(answerMaxtrixs2);
											}
										}
									}

								} else {
									List<MaxtrixCol> maxtrixCols = maxtriColDao.getOptionsByQuestionId(questionId);
									if (maxtrixRows != null && maxtrixRows != null) {
										for (MaxtrixRow maxtrixRow : maxtrixRows) {
											for (MaxtrixCol maxtrixCol : maxtrixCols) {
												answerMaxtrixs2 = getAnserMaxtrix(params, questionId + "_"
														+ maxtrixRow.getRowId() + "_" + maxtrixCol.getColId(), uuId);
												if (answerMaxtrixs2 != null && answerMaxtrixs2.size() > 0) {
													answerMaxtrixs.addAll(answerMaxtrixs2);
												}
											}

										}
									}
								}
							}

						}
					}
				}
			}

			if (answerOptions != null && answerOptions.size() > 0) {
				answerOptionDao.addByBach(answerOptions);
			}
			if (answerMaxtrixs != null && answerMaxtrixs.size() > 0) {
				answerMaxtrixDao.addByBach(answerMaxtrixs);
			}
			if (answerTexts != null && answerTexts.size() > 0) {
				answerTextDao.addByBach(answerTexts);
			}

		}

	}

	/**
	 * 
	 * @param params  参数集
	 * @param questionId_MaxtrixRowId  请求参数key
	 * @param uuId  回答者的标识
	 * @return 返回矩阵式问题的答案集合
	 */
	public List<AnswerMaxtrix> getAnserMaxtrix(Map<String, String[]> params, String questionId_MaxtrixRowId,
			String uuId) {
		String[] answers = params.get(questionId_MaxtrixRowId + "");
		List<AnswerMaxtrix> answerMaxtrixs = new ArrayList<>();
		Integer questionId = null;
		Integer maxtrixRowId = null;
		Integer maxtrixColId = null;
		Integer maxtrixOptionId = null;

		if (answers != null && answers.length > 0) {
			String[] ids = questionId_MaxtrixRowId.split("_");
			questionId = Integer.parseInt(ids[0]);
			maxtrixRowId = Integer.parseInt(ids[1]);

			if (ids.length == 2) {//矩阵式问题
				for (String colId : answers) {
					answerMaxtrixs.add(new AnswerMaxtrix(questionId, maxtrixRowId, Integer.parseInt(colId),
							maxtrixOptionId, uuId));
				}
			} else if (ids.length == 3) {//矩阵下拉式问题
				maxtrixColId = Integer.parseInt(ids[2]);
				for (String optionId : answers) {
					answerMaxtrixs.add(new AnswerMaxtrix(questionId, maxtrixRowId, maxtrixColId,
							Integer.parseInt(optionId), uuId));
				}

			}

			return answerMaxtrixs;
		}
		return null;
	}

	/**
	 * 
	 * @param questionId 问题标识
	 * @param params 请求的参数集合
	 * @param uuId 回答者的唯一标识
	 * @param answerTexts 如果还有文本其他项，则添加至文本集合
	 * @return 返回选择型的答案集合
	 */
	public List<AnswerOption> getAnswerOptionsOfQuestion(int questionId, Map<String, String[]> params, String uuId,
			List<AnswerText> answerTexts) {
		String[] answers = params.get(questionId + "");
		List<AnswerOption> answerOptions = new ArrayList<>();
		if (answers != null && answers.length > 0) {
			if (answers.length > 1) {// 防止总共有一个答案的时候，超出数组的边界
				if (answers[answers.length - 2].equals("0")) { //倒数第二个是其他 0 时，则代表此时是文本类型的其他项
					for (int i = 0; i < answers.length - 1; i++) { //除开文本答案
						answerOptions.add(new AnswerOption(questionId, Integer.parseInt(answers[i]), uuId));
					}

					answerTexts.add(new AnswerText(questionId, answers[answers.length - 1], uuId)); //添加文本答案,倒数最后一个是文本答案
					return answerOptions;

				} else { //选择全是选项类型，不含文本类其他问题
				
					for (String answer : answers) {
						if (!answer.equals("")) {
							answerOptions.add(new AnswerOption(questionId, Integer.parseInt(answer), uuId));
						}
					}
					return answerOptions;
				}
			}else { //问题阿德参数只有一个的情况
				for (String answer : answers) {
					if (!answer.equals("")) {
						answerOptions.add(new AnswerOption(questionId, Integer.parseInt(answer), uuId));
					}
				}
				return answerOptions;
			}
			

		}
		return null;
	}

	/**
	 * 
	 * @param questionId 问题的标识
	 * @param params 请求参数集合
	 * @param uuId 回答者的唯一标识
	 * @return text类型的答案集合
	 */
	public AnswerText getAnswerText(int questionId, Map<String, String[]> params, String uuId) {
		String[] answer = params.get(questionId + "");
		if (answer != null && answer.length > 0) { //
			return new AnswerText(questionId, answer[0], uuId);
		}
		return null;
	}

	/**
	 * 清除问题集合里问题对应 的答案 
	 */
	public void clearAnswersOfQuestions(List<Question> questions){
		if (questions != null && questions.size() > 0) {
			int questionTypeId = 0;
			int questionId = 0;
			int otherTypeId = 0;
			for (Question question : questions) {
				questionId = question.getQuestionId();
				questionTypeId = question.getQuestionTypeId();
				otherTypeId = question.getOtherType();
				if(questionTypeId < 7){//清除选项
					answerOptionDao.deleteByQuestionId(questionId);
					if (otherTypeId == 2 || questionTypeId == 6) {//其他项为文本或者 为文本类问题
						answerTextDao.deleteByQuestionId(questionId);
					}
				}else{//清除矩阵答案表里的答案
					answerMaxtrixDao.deleteByQuestionId(questionId);
				}
			}
		}
	}
}
