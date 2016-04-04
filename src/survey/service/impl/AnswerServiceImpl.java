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
	 * �����ʾ�Ĵ𰸣���uuId��ʶ��ͬ�Ļش���
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

							if (questionType < 6) { // ѡ�� + ������
								List<AnswerOption> answerOptions2 = getAnswerOptionsOfQuestion(questionId, params, uuId,
										answerTexts);
								if (answerOptions2 != null && answerOptions2.size() > 0) {
									answerOptions.addAll(answerOptions2);
								}

							} else if (questionType == 6) {// �ı�
								answerText = getAnswerText(questionId, params, uuId);
								if (answerText != null) {
									answerTexts.add(answerText);
								}
							} else {// ����ʽ
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
	 * @param params  ������
	 * @param questionId_MaxtrixRowId  �������key
	 * @param uuId  �ش��ߵı�ʶ
	 * @return ���ؾ���ʽ����Ĵ𰸼���
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

			if (ids.length == 2) {//����ʽ����
				for (String colId : answers) {
					answerMaxtrixs.add(new AnswerMaxtrix(questionId, maxtrixRowId, Integer.parseInt(colId),
							maxtrixOptionId, uuId));
				}
			} else if (ids.length == 3) {//��������ʽ����
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
	 * @param questionId �����ʶ
	 * @param params ����Ĳ�������
	 * @param uuId �ش��ߵ�Ψһ��ʶ
	 * @param answerTexts ��������ı��������������ı�����
	 * @return ����ѡ���͵Ĵ𰸼���
	 */
	public List<AnswerOption> getAnswerOptionsOfQuestion(int questionId, Map<String, String[]> params, String uuId,
			List<AnswerText> answerTexts) {
		String[] answers = params.get(questionId + "");
		List<AnswerOption> answerOptions = new ArrayList<>();
		if (answers != null && answers.length > 0) {
			if (answers.length > 1) {// ��ֹ�ܹ���һ���𰸵�ʱ�򣬳�������ı߽�
				if (answers[answers.length - 2].equals("0")) { //�����ڶ��������� 0 ʱ��������ʱ���ı����͵�������
					for (int i = 0; i < answers.length - 1; i++) { //�����ı���
						answerOptions.add(new AnswerOption(questionId, Integer.parseInt(answers[i]), uuId));
					}

					answerTexts.add(new AnswerText(questionId, answers[answers.length - 1], uuId)); //����ı���,�������һ�����ı���
					return answerOptions;

				} else { //ѡ��ȫ��ѡ�����ͣ������ı�����������
				
					for (String answer : answers) {
						if (!answer.equals("")) {
							answerOptions.add(new AnswerOption(questionId, Integer.parseInt(answer), uuId));
						}
					}
					return answerOptions;
				}
			}else { //���Ⱒ�²���ֻ��һ�������
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
	 * @param questionId ����ı�ʶ
	 * @param params �����������
	 * @param uuId �ش��ߵ�Ψһ��ʶ
	 * @return text���͵Ĵ𰸼���
	 */
	public AnswerText getAnswerText(int questionId, Map<String, String[]> params, String uuId) {
		String[] answer = params.get(questionId + "");
		if (answer != null && answer.length > 0) { //
			return new AnswerText(questionId, answer[0], uuId);
		}
		return null;
	}

	/**
	 * ������⼯���������Ӧ �Ĵ� 
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
				if(questionTypeId < 7){//���ѡ��
					answerOptionDao.deleteByQuestionId(questionId);
					if (otherTypeId == 2 || questionTypeId == 6) {//������Ϊ�ı����� Ϊ�ı�������
						answerTextDao.deleteByQuestionId(questionId);
					}
				}else{//�������𰸱���Ĵ�
					answerMaxtrixDao.deleteByQuestionId(questionId);
				}
			}
		}
	}
}
