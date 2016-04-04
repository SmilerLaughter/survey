package survey.model;

public class AnswerMaxtrix extends BaseAnswer{

	private Integer maxtrixRowId;
	private Integer maxtrixColId;
	private Integer maxtrixOptionId;
	

	public Integer getMaxtrixRowId() {
		return maxtrixRowId;
	}

	public void setMaxtrixRowId(Integer maxtrixRowId) {
		this.maxtrixRowId = maxtrixRowId;
	}

	public Integer getMaxtrixColId() {
		return maxtrixColId;
	}

	public void setMaxtrixColId(Integer maxtrixColId) {
		this.maxtrixColId = maxtrixColId;
	}

	public Integer getMaxtrixOptionId() {
		return maxtrixOptionId;
	}

	public void setMaxtrixOptionId(Integer maxtrixOptionId) {
		this.maxtrixOptionId = maxtrixOptionId;
	}

	@Override
	public String toString() {
		return "AnswerMaxtrix [questionId=" + questionId + ", maxtrixRowId=" + maxtrixRowId + ", maxtrixColId="
				+ maxtrixColId + ", maxtrixOptionId=" + maxtrixOptionId + "]";
	}

	public AnswerMaxtrix(Integer questionId, Integer maxtrixRowId, Integer maxtrixColId, Integer maxtrixOptionId,String uuId) {
		super();
		this.questionId = questionId;
		this.maxtrixRowId = maxtrixRowId;
		this.maxtrixColId = maxtrixColId;
		this.maxtrixOptionId = maxtrixOptionId;
		this.uuId = uuId;
	}

	public AnswerMaxtrix() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
