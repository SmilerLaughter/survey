package survey.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import survey.dao.MaxtriColDao;
import survey.dao.MaxtriOptionDao;
import survey.dao.MaxtriRowDao;
import survey.dao.OptionDao;
import survey.dao.OtherOptionDao;
import survey.dao.QuestionDao;
import survey.model.BaseOption;
import survey.model.MaxtriOption;
import survey.model.MaxtrixCol;
import survey.model.MaxtrixRow;
import survey.model.Option;
import survey.model.OtherOption;
import survey.model.Question;
import survey.service.AnswerService;
import survey.service.QuestionService;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDao questionDao;
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
	private AnswerService answerService;

	/**
	 * 新增 非矩形式 的 问题： 问题、所有选项、所有其他项选项
	 */
	@Override
	public void addQuestionNotMaxtri(Question model) {

		add(model);
		otherOperationOfAddNotMaxtri(model);

	}

	public void otherOperationOfAddNotMaxtri(Question model) {
		changeToOption(model);
		insertOptions(model.getOptions());

		if (model.getOtherType() == 3) { // 如果为3 ，所有是下拉式其他项，需要往数据库中添加数据
			changeToOtherOption(model);
			insertOtherOptions(model.getOtherSelecOptions());
		}
	}

	/**
	 * 把其他的下拉类型转换为选项集合,并设置问题的选项属性
	 */
	public void changeToOtherOption(Question model) {
		String[] arr = changeToArr(model.getOtherOptionStr());
		List<OtherOption> otherOptions = new ArrayList<>();
		int questionid = model.getQuestionId();

		if (arr != null && arr.length > 0) {
			for (String string : arr) {
				otherOptions.add(new OtherOption(string, questionid));
			}
			model.setOtherSelecOptions(otherOptions);

		}
	}

	/**
	 * 把矩形式的下拉列表 转换为选项集合,并设置问题的选项属性
	 */
	public void changeToMaxtriOption(Question model) {
		String[] arr = changeToArr(model.getMaxtrixOptionStr());
		List<MaxtriOption> maxtriOptions = new ArrayList<>();
		int questionId = model.getQuestionId();

		if (arr != null && arr.length > 0) {

			for (String string : arr) {
				maxtriOptions.add(new MaxtriOption(string, questionId));
			}
			model.setMaxtrixSelectOptions(maxtriOptions);
		}
	}

	/**
	 * 把矩形式的行标题 转换为选项集合,并设置问题的选项属性
	 * 
	 * @param model
	 */
	public void changeToMaxtriRow(Question model) {
		String[] arr = changeToArr(model.getMaxtrixRowStr());
		List<MaxtrixRow> maxtrixRows = new ArrayList<>();
		int questionId = model.getQuestionId();

		if (arr != null && arr.length > 0) {
			for (String string : arr) {
				maxtrixRows.add(new MaxtrixRow(string, questionId));
			}
			model.setMaxtrixRows(maxtrixRows);
		}

	}

	/**
	 * 把矩形式的列标题 转换为选项集合,并设置问题的选项属性
	 * 
	 * @param model
	 */
	public void changeToMaxtriCol(Question model) {
		String[] arr = changeToArr(model.getMaxtrixColStr());
		List<MaxtrixCol> maxtrixCols = new ArrayList<>();
		int questionId = model.getQuestionId();

		if (arr != null && arr.length > 0) {

			for (String string : arr) {
				maxtrixCols.add(new MaxtrixCol(string, questionId));
			}
			model.setMaxtrixCols(maxtrixCols);
		}
	}

	/**
	 * 把普通选项 转换为选项集合,并设置问题的选项属性
	 */
	public void changeToOption(Question model) {
		String[] arr = changeToArr(model.getOptionStr());
		List<Option> options = new ArrayList<>();
		int questionId = model.getQuestionId();
		if (arr != null && arr.length > 0) {
			for (String string : arr) {
				options.add(new Option(string, questionId));
			}
			model.setOptions(options);

		}
	}

	/**
	 * 把对应的字符串转换为数组，以回车分隔
	 */
	public String[] changeToArr(String str) {
		String[] strArr = null;
		if (str != null && !str.trim().equals("")) {
			strArr = str.split("\r\n");
		}
		return strArr;
	}

	/**
	 * 插入数据库
	 */
	public void insertOptions(List<Option> options) {
		if (options != null && options.size() > 0) {

			optionDao.addByBach(options);
		}
	}

	/**
	 * 插入数据库
	 */
	public void insertOtherOptions(List<OtherOption> otherOptions) {
		if (otherOptions != null && otherOptions.size() > 0) {
			otheroptionDao.addByBach(otherOptions);
		}
	}

	/**
	 * 插入数据库
	 */
	public void insertMaxtriRow(List<MaxtrixRow> maxtriRow) {
		if (maxtriRow != null && maxtriRow.size() > 0) {
			maxtriRowDao.addByBach(maxtriRow);
		}

	}

	/**
	 * 插入数据库
	 */
	public void insertMaxtriCol(List<MaxtrixCol> maxtriCol) {
		if (maxtriCol != null && maxtriCol.size() > 0) {
			maxtriColDao.addByBach(maxtriCol);
		}

	}

	/**
	 * 插入数据库
	 */
	public void insertMaxtriOption(List<MaxtriOption> maxtriOption) {
		if (maxtriOption != null && maxtriOption.size() > 0) {

			maxtriOptionDao.addByBach(maxtriOption);
		}
	}

	/**
	 * 对于矩阵式问题的 行标题、列标题、其他项的操作
	 * 
	 * @param model
	 */
	private void otherOperationOfAddMaxtri(Question model) {
		// TODO Auto-generated method stub
		changeToMaxtriOption(model);
		insertMaxtriOption(model.getMaxtrixSelectOptions());

		changeToMaxtriCol(model);
		insertMaxtriCol(model.getMaxtrixCols());

		changeToMaxtriRow(model);
		insertMaxtriRow(model.getMaxtrixRows());
	}

	/**
	 * 添加矩阵式问题:行标题、列标题、下拉选项
	 */
	@Override
	public void addQuestionIsMaxtri(Question model) {
		model.setMaxtri(true);
		add(model);
		otherOperationOfAddMaxtri(model);

	}

	/**
	 * 新增问题
	 */
	@Override
	public void add(Question model) {

		questionDao.add(model);
	}

	/**
	 * 通过 id 获取 对应的问题
	 */
	@Override
	public Question getById(Integer id) {
		// TODO Auto-generated method stub
		return questionDao.getById(id);
	}

	/**
	 * 删除问题选项,和问题答案
	 */
	public void deleteOptions(Integer id) {
		Question question = questionDao.getById(id);
		List<Question> questions = new ArrayList<>();
		questions.add(question);
		answerService.clearAnswersOfQuestions(questions);

		if (question.getOtherType() == 3) {// 为3时，其他项为下拉列表，删除对应的选项
			otheroptionDao.deleteOptionsByQuestionId(id);
		}
		if (question.getQuestionTypeId() < 6) { // 非矩阵式问题，删除对应选项
			optionDao.deleteOptionsByQuestionId(id);
		} else if (question.getQuestionTypeId() > 6) {// 矩阵式问题，删除行标题、列标题
			maxtriColDao.deleteOptionsByQuestionId(id);
			maxtriRowDao.deleteOptionsByQuestionId(id);
			if (question.getQuestionTypeId() == 9) { // 矩阵下拉式，删除行标题、列标题、以及下拉列表
				maxtriOptionDao.deleteOptionsByQuestionId(id);
			}
		}
	}

	/**
	 * 删除问题
	 */
	@Override
	public void deleteQuestion(Integer id) {
		deleteOptions(id);
		questionDao.delete(id);

	}

	/**
	 * 把数据库中的问题选项记录转换为字符串，用于页面回显
	 * 
	 * @param options
	 * @return
	 */
	public String changeToStr(List<? extends BaseOption> options) {
		StringBuffer buffer = new StringBuffer("");
		if (options != null && options.size() > 0) {
			for (BaseOption baseOption : options) {
				buffer.append(baseOption.getContent() + "\n");
			}
		}

		return buffer.toString();
	}

	/**
	 * 获取对应的问题，把问题对应的选项记录转换为字符串
	 */
	public Question getEditQuestion(Integer questionId) {

		Question question = questionDao.getById(questionId);
		int questionTypeId = question.getQuestionTypeId();
		if (questionTypeId < 6) {
			question.setOptionStr(changeToStr(optionDao.getOptionsByQuestionId(questionId)));
		} else if (questionTypeId > 6) {
			question.setMaxtrixColStr(changeToStr(maxtriColDao.getOptionsByQuestionId(questionId)));
			question.setMaxtrixRowStr(changeToStr(maxtriRowDao.getOptionsByQuestionId(questionId)));
			if (questionTypeId == 9) {
				question.setMaxtrixOptionStr(changeToStr(maxtriOptionDao.getOptionsByQuestionId(questionId)));
			}
		}
		if (question.getOtherType() == 3) {
			question.setOtherOptionStr(changeToStr(otheroptionDao.getOptionsByQuestionId(questionId)));
		}

		return question;
	}

	/**
	 * 修改问题：进行问题的更新操作 及其所有选项的删除，然后再进行添加操作---由于修改后选项的个数可能发生变化
	 * 
	 */
	@Override
	public void updateQuestionNotMaxtri(Question model) {
		questionDao.update(model);
		deleteOptions(model.getQuestionId());
		otherOperationOfAddNotMaxtri(model);
	}

	/**
	 * 修改问题：进行问题的更新操作 及其所有选项的删除，然后再进行添加操作---由于修改后选项的个数可能发生变化
	 * 
	 */
	@Override
	public void updateQuestionIsMaxtri(Question model) {
		questionDao.update(model);
		deleteOptions(model.getQuestionId());
		otherOperationOfAddMaxtri(model);
	}

	/**
	 * 获取问题以及其选项
	 */
	@Override
	public Question getQuestionWithOptions(Integer questionId) {
		Question question = getById(questionId);
		List<Question> questions = new ArrayList<>();
		setOptionsOfQuestion(question);

		return question;
	}

	/**
	 * 为单个问题设置选项
	 */
	public void setOptionsOfQuestion(Question question) {
		int questionId = 0;
		int questionTypeId = 0;

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
