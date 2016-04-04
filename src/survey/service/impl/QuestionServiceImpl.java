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
	 * ���� �Ǿ���ʽ �� ���⣺ ���⡢����ѡ�����������ѡ��
	 */
	@Override
	public void addQuestionNotMaxtri(Question model) {

		add(model);
		otherOperationOfAddNotMaxtri(model);

	}

	public void otherOperationOfAddNotMaxtri(Question model) {
		changeToOption(model);
		insertOptions(model.getOptions());

		if (model.getOtherType() == 3) { // ���Ϊ3 ������������ʽ�������Ҫ�����ݿ����������
			changeToOtherOption(model);
			insertOtherOptions(model.getOtherSelecOptions());
		}
	}

	/**
	 * ����������������ת��Ϊѡ���,�����������ѡ������
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
	 * �Ѿ���ʽ�������б� ת��Ϊѡ���,�����������ѡ������
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
	 * �Ѿ���ʽ���б��� ת��Ϊѡ���,�����������ѡ������
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
	 * �Ѿ���ʽ���б��� ת��Ϊѡ���,�����������ѡ������
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
	 * ����ͨѡ�� ת��Ϊѡ���,�����������ѡ������
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
	 * �Ѷ�Ӧ���ַ���ת��Ϊ���飬�Իس��ָ�
	 */
	public String[] changeToArr(String str) {
		String[] strArr = null;
		if (str != null && !str.trim().equals("")) {
			strArr = str.split("\r\n");
		}
		return strArr;
	}

	/**
	 * �������ݿ�
	 */
	public void insertOptions(List<Option> options) {
		if (options != null && options.size() > 0) {

			optionDao.addByBach(options);
		}
	}

	/**
	 * �������ݿ�
	 */
	public void insertOtherOptions(List<OtherOption> otherOptions) {
		if (otherOptions != null && otherOptions.size() > 0) {
			otheroptionDao.addByBach(otherOptions);
		}
	}

	/**
	 * �������ݿ�
	 */
	public void insertMaxtriRow(List<MaxtrixRow> maxtriRow) {
		if (maxtriRow != null && maxtriRow.size() > 0) {
			maxtriRowDao.addByBach(maxtriRow);
		}

	}

	/**
	 * �������ݿ�
	 */
	public void insertMaxtriCol(List<MaxtrixCol> maxtriCol) {
		if (maxtriCol != null && maxtriCol.size() > 0) {
			maxtriColDao.addByBach(maxtriCol);
		}

	}

	/**
	 * �������ݿ�
	 */
	public void insertMaxtriOption(List<MaxtriOption> maxtriOption) {
		if (maxtriOption != null && maxtriOption.size() > 0) {

			maxtriOptionDao.addByBach(maxtriOption);
		}
	}

	/**
	 * ���ھ���ʽ����� �б��⡢�б��⡢������Ĳ���
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
	 * ��Ӿ���ʽ����:�б��⡢�б��⡢����ѡ��
	 */
	@Override
	public void addQuestionIsMaxtri(Question model) {
		model.setMaxtri(true);
		add(model);
		otherOperationOfAddMaxtri(model);

	}

	/**
	 * ��������
	 */
	@Override
	public void add(Question model) {

		questionDao.add(model);
	}

	/**
	 * ͨ�� id ��ȡ ��Ӧ������
	 */
	@Override
	public Question getById(Integer id) {
		// TODO Auto-generated method stub
		return questionDao.getById(id);
	}

	/**
	 * ɾ������ѡ��,�������
	 */
	public void deleteOptions(Integer id) {
		Question question = questionDao.getById(id);
		List<Question> questions = new ArrayList<>();
		questions.add(question);
		answerService.clearAnswersOfQuestions(questions);

		if (question.getOtherType() == 3) {// Ϊ3ʱ��������Ϊ�����б�ɾ����Ӧ��ѡ��
			otheroptionDao.deleteOptionsByQuestionId(id);
		}
		if (question.getQuestionTypeId() < 6) { // �Ǿ���ʽ���⣬ɾ����Ӧѡ��
			optionDao.deleteOptionsByQuestionId(id);
		} else if (question.getQuestionTypeId() > 6) {// ����ʽ���⣬ɾ���б��⡢�б���
			maxtriColDao.deleteOptionsByQuestionId(id);
			maxtriRowDao.deleteOptionsByQuestionId(id);
			if (question.getQuestionTypeId() == 9) { // ��������ʽ��ɾ���б��⡢�б��⡢�Լ������б�
				maxtriOptionDao.deleteOptionsByQuestionId(id);
			}
		}
	}

	/**
	 * ɾ������
	 */
	@Override
	public void deleteQuestion(Integer id) {
		deleteOptions(id);
		questionDao.delete(id);

	}

	/**
	 * �����ݿ��е�����ѡ���¼ת��Ϊ�ַ���������ҳ�����
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
	 * ��ȡ��Ӧ�����⣬�������Ӧ��ѡ���¼ת��Ϊ�ַ���
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
	 * �޸����⣺��������ĸ��²��� ��������ѡ���ɾ����Ȼ���ٽ�����Ӳ���---�����޸ĺ�ѡ��ĸ������ܷ����仯
	 * 
	 */
	@Override
	public void updateQuestionNotMaxtri(Question model) {
		questionDao.update(model);
		deleteOptions(model.getQuestionId());
		otherOperationOfAddNotMaxtri(model);
	}

	/**
	 * �޸����⣺��������ĸ��²��� ��������ѡ���ɾ����Ȼ���ٽ�����Ӳ���---�����޸ĺ�ѡ��ĸ������ܷ����仯
	 * 
	 */
	@Override
	public void updateQuestionIsMaxtri(Question model) {
		questionDao.update(model);
		deleteOptions(model.getQuestionId());
		otherOperationOfAddMaxtri(model);
	}

	/**
	 * ��ȡ�����Լ���ѡ��
	 */
	@Override
	public Question getQuestionWithOptions(Integer questionId) {
		Question question = getById(questionId);
		List<Question> questions = new ArrayList<>();
		setOptionsOfQuestion(question);

		return question;
	}

	/**
	 * Ϊ������������ѡ��
	 */
	public void setOptionsOfQuestion(Question question) {
		int questionId = 0;
		int questionTypeId = 0;

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
