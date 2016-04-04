package survey.model;

public class MaxtriOption extends BaseOption{

	private Integer optionId;
	
	public MaxtriOption(String content, int questionId) {
	
		this.content = content;
		this.questionId = questionId;
		// TODO Auto-generated constructor stub
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}
	
	public Integer getOptionId() {
		return optionId;
	}
	
	public MaxtriOption() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "MaxtriOption [optionId=" + optionId + ", content=" + content + ", questionId=" + questionId + "]";
	}
	
	

}
