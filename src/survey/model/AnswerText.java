package survey.model;

public class AnswerText extends BaseAnswer{

	private String answerContent;

	public AnswerText() {
		// TODO Auto-generated constructor stub
	}

	
	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	@Override
	public String toString() {
		return "AnswerText [questionId=" + questionId + ", answerContent=" + answerContent + "]";
	}

	public AnswerText(Integer questionId, String answerContent,String uuId) {
		super();
		this.questionId = questionId;
		this.answerContent = answerContent;
		this.uuId = uuId;
	}
	
	
}
