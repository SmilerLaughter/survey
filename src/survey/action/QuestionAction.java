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

	private Integer surveyId;// 调转到对应的 survey页面时，需要surveyId

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	/**
	 * 
	 * @return 调转到问题类型页面
	 */
	public String toAddQuestion() {

		return "toAddQuestionPage";
	}

	/**
	 * 调转到问题对应的表单页面
	 * 
	 * @return 问题类型id
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
	 * 根据问题类型进行 不同的添加操作
	 * 
	 * @return 调转到当前 survey 页面
	 */
	public String addOrUpdateQuestion() {

		int questionTypeId = model.getQuestionTypeId();
		if (model.getQuestionId() == null) { // 新增操作
			if (questionTypeId < 7) {
				questionService.addQuestionNotMaxtri(model);
			} else {
				questionService.addQuestionIsMaxtri(model);
			}
		} else {// 更新操作
			if (questionTypeId < 7) {
				questionService.updateQuestionNotMaxtri(model);
			} else {
				questionService.updateQuestionIsMaxtri(model);
			}
		}
		return "designAction";
	}

	/**
	 * 删除问题以及对应的所有选项
	 * 
	 * @return 跳转到当前survey 页面
	 */
	public String deleteQuestion() {

		questionService.deleteQuestion(model.getQuestionId());
		return "designAction";
	}
}
