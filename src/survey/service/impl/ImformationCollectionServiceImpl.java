package survey.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import survey.dao.StatisticDao;
import survey.model.AnswerMaxtrix;
import survey.model.AnswerOption;
import survey.model.AnswerText;
import survey.model.MaxtriOption;
import survey.model.MaxtrixCol;
import survey.model.MaxtrixRow;
import survey.model.Option;
import survey.model.Page;
import survey.model.Question;
import survey.model.Survey;
import survey.service.ImformationCollectionService;
import survey.service.SurveyService;

@Service("imformationCollectionService")
public class ImformationCollectionServiceImpl implements ImformationCollectionService {

	@Autowired
	private SurveyService surveyService;
	private Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	@Autowired
	private StatisticDao statisticDao;

	
	/**
	 * ��ȡ������ excel����
	 */
	
	@Override
	public HSSFWorkbook getWorkBook(Survey model) {

		Survey survey = surveyService.getSurveyBySurveyId(model.getSurveyId()); //��ȡsurvey
		HSSFWorkbook workbook = new HSSFWorkbook(); //����������
		String[] uuids = statisticDao.getUuIds(model.getSurveyId()); //��ȡ��ǰsurvey �Ļش��߼���
		setWorkbookTitle(workbook, survey, uuids.length); //���ù�������һ�У�������Ŀ�Լ�ѡ��

		collectionAnswers(workbook, uuids); // �ռ��𰸣������ý�excel������
		return workbook;
	}
	
	/**
	 * �ռ��𰸣��������excel�ĵ�Ԫ����
	 * @param workBook 
	 * @param uuIds
	 */
	public void collectionAnswers(HSSFWorkbook workBook, String[] uuIds) {
		int i = 1;
		HSSFSheet sheet = workBook.getSheetAt(0); 
		List<AnswerOption> answerOptions = null;
		List<AnswerMaxtrix> answerMaxtrixs = null;
		List<AnswerText> answerTexts = null;

		if (uuIds.length > 0) {
			for (String uuId : uuIds) {
				HSSFRow row = sheet.getRow(i++);

				answerOptions = statisticDao.getOptionAnswers(uuId); //ѡ����𰸼���
				answerMaxtrixs = statisticDao.getMaxtrixAnswers(uuId); //����ʽ�𰸼���
				answerTexts = statisticDao.getTextAnswers(uuId); //�ı�ʽ�𰸼���
				setAnswersToExcel(answerOptions, answerMaxtrixs, answerTexts, row); //���õ�Ԫ���ֵ
			}
		}
	}

	/**
	 * Ϊ��Ԫ�����ô�
	 * @param answerOptions  ѡ����𰸼���
	 * @param answerMaxtrixs  ����ʽ�𰸼���
	 * @param answerTexts  �ı�ʽ�𰸼���
	 * @param row ��Ӧ����
	 */
	public void setAnswersToExcel(List<AnswerOption> answerOptions, List<AnswerMaxtrix> answerMaxtrixs,
			List<AnswerText> answerTexts, HSSFRow row) {

		String str = "";
		if (answerOptions != null && answerOptions.size() > 0) { 
			for (AnswerOption answerOption : answerOptions) {
				str = row.getCell(map.get(answerOption.getQuestionId())).getStringCellValue(); // ��excel�л�ȡ��Ӧ�ĵ�Ԫ������Ϊ���ڶ�ѡ������Ӷ��ֵ��������Ҫ��ȡ����ֵ��Ȼ���ٽ���׷��
				row.getCell(map.get(answerOption.getQuestionId())).setCellValue(str + "  " + answerOption.getOptionId());
			}
		}
		if (answerMaxtrixs != null && answerMaxtrixs.size() > 0) {
			for (AnswerMaxtrix answerMaxtrix : answerMaxtrixs) {// ��excel�л�ȡ��Ӧ�ĵ�Ԫ������Ϊ���ڶ�ѡ������Ӷ��ֵ��������Ҫ��ȡ����ֵ��Ȼ���ٽ���׷��
				str = row.getCell(map.get(answerMaxtrix.getQuestionId())).getStringCellValue();
				
				if (answerMaxtrix.getMaxtrixOptionId() != null) { //��������ʽ�������⣬
					row.getCell(map.get(answerMaxtrix.getQuestionId())).setCellValue(  str + "   "  +
							answerMaxtrix.getMaxtrixRowId() + "_"
							+ answerMaxtrix.getMaxtrixColId() );
				}else {//���ڵ����ľ���ʽ����
					row.getCell(map.get(answerMaxtrix.getQuestionId())).setCellValue(  str + "   "  +
							answerMaxtrix.getMaxtrixRowId() + "_"
							+ answerMaxtrix.getMaxtrixColId() );
				}
				
			}
		}
		if (answerTexts != null && answerTexts.size() > 0) {
			for (AnswerText answerText : answerTexts) { //�ı�ʽ����
				str = row.getCell(map.get(answerText.getQuestionId())).getStringCellValue();
				row.getCell(map.get(answerText.getQuestionId())).setCellValue(str +  answerText.getAnswerContent());
			}
		}
		

	}

	/**
	 * ����survey��������ʾ
	 * @param workbook ������
	 * @param survey ��ǰsurvey����Ϊȡ��������׼��
	 * @param rows ��Ҫ�����𰸵�����������uuId�Ķ������ж�
	 */
	public void setWorkbookTitle(HSSFWorkbook workbook, Survey survey, int rows) {
		HSSFSheet sheet = workbook.createSheet(survey.getTitle());
		HSSFRow row = sheet.createRow(0);

		List<Page> pages = survey.getPages();

		if (survey.getPages() != null && survey.getPages().size() > 0) {
			int i = 0;
			StringBuffer strBuffer;
			for (Page page : pages) {
				List<Question> questions = page.getQuestions();
				if (questions != null && questions.size() > 0) {
					for (Question question : questions) {
						strBuffer = new StringBuffer("");
						strBuffer.append(question.getTitle());

						if (question.getQuestionTypeId() < 5) {
							if (question.getOptions() != null) {
								for (Option option : question.getOptions()) {
									strBuffer.append("    ");
									strBuffer.append(option.getOptionId() + "." + option.getContent());
								}
							}
							if (question.getOtherType() != 0) {
								strBuffer.append("   0.����");
							}
						} else if (question.getQuestionTypeId() > 6) {
							strBuffer.append("    ");
							for (MaxtrixCol maxtrixCol : question.getMaxtrixCols()) {
								strBuffer.append(maxtrixCol.getColId() + "." + maxtrixCol.getContent() + "  ");
							}
							for (MaxtrixRow maxtrixRow : question.getMaxtrixRows()) {
								strBuffer.append("   " + maxtrixRow.getRowId() + "." + maxtrixRow.getContent());
							}
							if (question.getQuestionTypeId() == 9) {
								strBuffer.append("    ����ѡ���У�");
								for (MaxtriOption maxtriOption : question.getMaxtrixSelectOptions()) {
									strBuffer.append(
											maxtriOption.getOptionId() + "." + maxtriOption.getContent() + "   ");
								}
							}
						}
						map.put(question.getQuestionId(),i);
						row.createCell(i++).setCellValue(strBuffer.toString());
					}
				}
			}
			for (int j = 1; j <= rows; j++) { //������Ԫ�񣬸�������ĸ��� �� �ش������趨
				HSSFRow answerRow = sheet.createRow(j);
				for (int j2 = 0; j2 < i; j2++) {
					answerRow.createCell(j2);
				}
			}

		}
	}

}
