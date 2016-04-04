package survey.model;

public class MaxtrixCol extends BaseOption{

	private Integer colId;
	
	public void setColId(Integer colId) {
		this.colId = colId;
	}
	
	public Integer getColId() {
		return colId;
	}
	
	public MaxtrixCol() {
		// TODO Auto-generated constructor stub
	}
	
	public MaxtrixCol(String content,int questionId){
		this.content = content;
		this.questionId = questionId;
	}

	@Override
	public String toString() {
		return "MaxtrixCol [colId=" + colId + ", content=" + content + ", questionId=" + questionId + "]";
	}
	
}
