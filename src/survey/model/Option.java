package survey.model;

public class Option extends BaseOption{

	private Integer optionId;
	
	public Option(String content, int questionId) {
		this.content = content;
		this.questionId = questionId;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public Option() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Option [optionId=" + optionId + ", content=" + content + ", questionId=" + questionId + "]";
	}

	
}
