package survey.model;

public class AnswerOption extends BaseAnswer {

	private Integer optionId;

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	@Override
	public String toString() {
		return "AnswerOption [questionId=" + questionId + ", optionId=" + optionId + "]";
	}

	public AnswerOption(Integer questionId, Integer optionId,String uuId) {
		super();
		this.questionId = questionId;
		this.optionId = optionId;
		this.uuId = uuId;
	}

	public AnswerOption() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
