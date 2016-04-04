package survey.model;

public class MaxtrixRow extends BaseOption{

	private Integer rowId;
	
	public Integer getRowId() {
		return rowId;
	}
	
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	
	public MaxtrixRow() {
		// TODO Auto-generated constructor stub
	}
	
	public MaxtrixRow(String content,int questionId){
		this.content = content;
		this.questionId = questionId;
	}

	@Override
	public String toString() {
		return "MaxtrixRow [rowId=" + rowId + ", content=" + content + ", questionId=" + questionId + "]";
	}


	
}
