package survey.model;

import java.io.Serializable;
import java.util.List;

public class Page implements Serializable{

	private transient Integer pageId;
	private String title = "Î´ÃüÃû";
	private String description = "ÎÞ";
	private List<Question> questions;
	private transient Integer surveyId;
	private transient float orderNo;
	
	public void setOrderNo(float order) {
		this.orderNo = order;
	}
	
	public float getOrderNo() {
		return orderNo;
	}
	
	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}
	
	public Integer getSurveyId() {
		return surveyId;
	}
	
	
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public List<Question> getQuestions() {
		return questions;
	}
	
	
	public Page() {
		// TODO Auto-generated constructor stub
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer id) {
		this.pageId = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String titlle) {
		this.title = titlle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Page( String titlle, String description) {
		super();
		this.title = titlle;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Page [pageId=" + pageId + ", title=" + title + ", description=" + description + ", questions="
				+ questions + ", surveyId=" + surveyId + ", order=" + orderNo + "]";
	}

	public Page(String titlle, String description, List<Question> questions) {
		super();
		this.title = titlle;
		this.description = description;
		this.questions = questions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + Float.floatToIntBits(orderNo);
		result = prime * result + ((pageId == null) ? 0 : pageId.hashCode());
		result = prime * result + ((questions == null) ? 0 : questions.hashCode());
		result = prime * result + ((surveyId == null) ? 0 : surveyId.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page other = (Page) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (Float.floatToIntBits(orderNo) != Float.floatToIntBits(other.orderNo))
			return false;
		if (pageId == null) {
			if (other.pageId != null)
				return false;
		} else if (!pageId.equals(other.pageId))
			return false;
		if (questions == null) {
			if (other.questions != null)
				return false;
		} else if (!questions.equals(other.questions))
			return false;
		if (surveyId == null) {
			if (other.surveyId != null)
				return false;
		} else if (!surveyId.equals(other.surveyId))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	
	
	
}
