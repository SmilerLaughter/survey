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
	 * 获取工作薄 excel对象
	 */
	
	@Override
	public HSSFWorkbook getWorkBook(Survey model) {

		Survey survey = surveyService.getSurveyBySurveyId(model.getSurveyId()); //获取survey
		HSSFWorkbook workbook = new HSSFWorkbook(); //创建工作薄
		String[] uuids = statisticDao.getUuIds(model.getSurveyId()); //获取当前survey 的回答者集合
		setWorkbookTitle(workbook, survey, uuids.length); //设置工作薄第一行：问题题目以及选项

		collectionAnswers(workbook, uuids); // 收集答案，并设置进excel对象中
		return workbook;
	}
	
	/**
	 * 收集答案，并添加至excel的单元格中
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

				answerOptions = statisticDao.getOptionAnswers(uuId); //选择题答案集合
				answerMaxtrixs = statisticDao.getMaxtrixAnswers(uuId); //矩形式答案集合
				answerTexts = statisticDao.getTextAnswers(uuId); //文本式答案集合
				setAnswersToExcel(answerOptions, answerMaxtrixs, answerTexts, row); //设置单元格的值
			}
		}
	}

	/**
	 * 为单元格设置答案
	 * @param answerOptions  选择题答案集合
	 * @param answerMaxtrixs  矩形式答案集合
	 * @param answerTexts  文本式答案集合
	 * @param row 对应的行
	 */
	public void setAnswersToExcel(List<AnswerOption> answerOptions, List<AnswerMaxtrix> answerMaxtrixs,
			List<AnswerText> answerTexts, HSSFRow row) {

		String str = "";
		if (answerOptions != null && answerOptions.size() > 0) { 
			for (AnswerOption answerOption : answerOptions) {
				str = row.getCell(map.get(answerOption.getQuestionId())).getStringCellValue(); // 从excel中获取对应的单元对象，因为对于多选，会添加多个值，所以需要先取出其值，然后再进行追加
				row.getCell(map.get(answerOption.getQuestionId())).setCellValue(str + "  " + answerOption.getOptionId());
			}
		}
		if (answerMaxtrixs != null && answerMaxtrixs.size() > 0) {
			for (AnswerMaxtrix answerMaxtrix : answerMaxtrixs) {// 从excel中获取对应的单元对象，因为对于多选，会添加多个值，所以需要先取出其值，然后再进行追加
				str = row.getCell(map.get(answerMaxtrix.getQuestionId())).getStringCellValue();
				
				if (answerMaxtrix.getMaxtrixOptionId() != null) { //对于下拉式矩形问题，
					row.getCell(map.get(answerMaxtrix.getQuestionId())).setCellValue(  str + "   "  +
							answerMaxtrix.getMaxtrixRowId() + "_"
							+ answerMaxtrix.getMaxtrixColId() );
				}else {//对于单纯的矩形式问题
					row.getCell(map.get(answerMaxtrix.getQuestionId())).setCellValue(  str + "   "  +
							answerMaxtrix.getMaxtrixRowId() + "_"
							+ answerMaxtrix.getMaxtrixColId() );
				}
				
			}
		}
		if (answerTexts != null && answerTexts.size() > 0) {
			for (AnswerText answerText : answerTexts) { //文本式问题
				str = row.getCell(map.get(answerText.getQuestionId())).getStringCellValue();
				row.getCell(map.get(answerText.getQuestionId())).setCellValue(str +  answerText.getAnswerContent());
			}
		}
		

	}

	/**
	 * 设置survey的问题显示
	 * @param workbook 工作薄
	 * @param survey 当前survey对象，为取出问题做准备
	 * @param rows 需要创建答案的行数，根据uuId的多少来判断
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
								strBuffer.append("   0.其他");
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
								strBuffer.append("    其中选项有：");
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
			for (int j = 1; j <= rows; j++) { //创建单元格，根据问题的个数 和 回答人数设定
				HSSFRow answerRow = sheet.createRow(j);
				for (int j2 = 0; j2 < i; j2++) {
					answerRow.createCell(j2);
				}
			}

		}
	}

}
