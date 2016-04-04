package survey.model;

public class BaseAnswer {

	protected Integer questionId;
	protected String uuId;

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	@Override
	public String toString() {
		return "BaseAnswer [questionId=" + questionId + ", uuId=" + uuId + "]";
	}
	
	

}
