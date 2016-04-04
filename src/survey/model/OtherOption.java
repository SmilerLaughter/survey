package survey.model;

public class OtherOption extends BaseOption{
	private int otherOptionId;
	
	public int getOtherOptionId()  {
		return otherOptionId;
	}

	public void setOtherOptionId(int otherOptionId) {
		this.otherOptionId = otherOptionId;
	}


	@Override
	public String toString() {
		return "OtherOption [otherOptionId=" + otherOptionId + ", content=" + content + ", questionId=" + questionId
				+ "]";
	}

	public OtherOption(String content, int questionId) {
		super();
		this.content = content;
		this.questionId = questionId;
	}

	public OtherOption() {
		// TODO Auto-generated constructor stub
	}
}
