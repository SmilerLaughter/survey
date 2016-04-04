package survey.model;

public class MonthExcel {

	private Integer monthExcelId;
	private Integer surveyId;
	private String url;
	private String fileDate;

	
	
	public Integer getMonthExcelId() {
		return monthExcelId;
	}

	public void setMonthExcelId(Integer monthExcelId) {
		this.monthExcelId = monthExcelId;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public String getFileDate() {
		return fileDate;
	}

	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "MonthStatistic [surveyId=" + surveyId + ", url=" + url + ", fileDate=" + fileDate + "]";
	}

	public MonthExcel(Integer surveyId, String url, String fileDate) {
		super();
		this.surveyId = surveyId;
		this.url = url;
		this.fileDate = fileDate;
	}

	public MonthExcel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
