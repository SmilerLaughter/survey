package survey.model;

import java.io.Serializable;

public class BaseOption implements Serializable{

	protected String content;
	protected Integer questionId;
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	
	public String getContent() {
		return content;
	}
	
	public Integer getQuestionId() {
		return questionId;
	}


	public BaseOption(String content, Integer questionId) {
		super();
		this.content = content;
		this.questionId = questionId;
	}
	
	public BaseOption() {
		// TODO Auto-generated constructor stub
	}
	
}
