package survey.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import survey.model.Question;
import survey.service.QuestionService;

@Scope("prototype")
@Controller
public class QuestionAction extends BaseAction<Question> {

	@Autowired
	private QuestionService questionService;

	private Integer surveyId;// ��ת����Ӧ�� surveyҳ��ʱ����ҪsurveyId

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	/**
	 * 
	 * @return ��ת����������ҳ��
	 */
	public String toAddQuestion() {

		return "toAddQuestionPage";
	}

	/**
	 * ��ת�������Ӧ�ı�ҳ��
	 * 
	 * @return ��������id
	 */
	public String toQuestionType() {

		return model.getQuestionTypeId() + "";
	}

	/**
	 * 
	 */
	public String toEditQuestion() {

		model = questionService.getEditQuestion(model.getQuestionId());
		return model.getQuestionTypeId() + "";
	}

	/**
	 * �����������ͽ��� ��ͬ����Ӳ���
	 * 
	 * @return ��ת����ǰ survey ҳ��
	 */
	public String addOrUpdateQuestion() {

		int questionTypeId = model.getQuestionTypeId();
		if (model.getQuestionId() == null) { // ��������
			if (questionTypeId < 7) {
				questionService.addQuestionNotMaxtri(model);
			} else {
				questionService.addQuestionIsMaxtri(model);
			}
		} else {// ���²���
			if (questionTypeId < 7) {
				questionService.updateQuestionNotMaxtri(model);
			} else {
				questionService.updateQuestionIsMaxtri(model);
			}
		}
		return "designAction";
	}

	/**
	 * ɾ�������Լ���Ӧ������ѡ��
	 * 
	 * @return ��ת����ǰsurvey ҳ��
	 */
	public String deleteQuestion() {

		questionService.deleteQuestion(model.getQuestionId());
		return "designAction";
	}
}
