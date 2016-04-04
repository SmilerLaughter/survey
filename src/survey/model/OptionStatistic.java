package survey.model;

public class OptionStatistic {

	private Integer optionId;
	private String content = "ÆäËû";
	private long optionCount;
	
	
	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	
	public String getContent() {
		return content;
	}
	

	public Integer getOptionId() {
		return optionId;
	}

	public OptionStatistic(Integer optionId, String content, long count) {
		super();
		this.optionId = optionId;
		this.content = content;
		this.optionCount = count;
	}

	public long getOptionCount() {
		return optionCount;
	}

	public void setOptionCount(long optionCount) {
		this.optionCount = optionCount;
	}

	@Override
	public String toString() {
		return "OptionStatistic [optionId=" + optionId + ", content=" + content + ", count=" + optionCount + "]";
	}
	
	public OptionStatistic() {
		// TODO Auto-generated constructor stub
	}
	
}
