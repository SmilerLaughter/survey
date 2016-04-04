package survey.model;

import java.util.Date;
import java.util.List;

public class Survey {
	
	private  Integer surveyId;
	private String  title = "未命名";
	private String preText = "上一步";
	private  String nextText = "下一步";
	private  String  exitText = "退出";
	private String doneText = "完成";
	private Date createTime = new Date();
	private List<Page> pages ;
	private Integer userId;
	private boolean closed = true;
	private String logoPath;
	
	private float minOrder;
	private float maxOrder;
	
	public void setMaxOrder(float maxOrder) {
		this.maxOrder = maxOrder;
	}
	
	public void setMinOrder(float minOrder) {
		this.minOrder = minOrder;
	}
	
	public float getMaxOrder() {
		return maxOrder;
	}
	
	public float getMinOrder() {
		return minOrder;
	}
	
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
	public String getLogoPath() {
		return logoPath;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	public void setPages(List<Page> pages) {
		this.pages = pages;
	}
	
	public List<Page> getPages() {
		return pages;
	}
	
	public Survey() {
		// TODO Auto-generated constructor stub
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPreText() {
		return preText;
	}

	public void setPreText(String preText) {
		this.preText = preText;
	}

	public String getNextText() {
		return nextText;
	}

	public void setNextText(String nextText) {
		this.nextText = nextText;
	}

	public String getExitText() {
		return exitText;
	}

	public void setExitText(String exitText) {
		this.exitText = exitText;
	}

	public String getDoneText() {
		return doneText;
	}

	public void setDoneText(String doneText) {
		this.doneText = doneText;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	
	public Survey(String title, String preText, String nextText, String exitText, String doneText, Date createTime,
			List<Page> pages, Integer userId) {
		super();
		this.title = title;
		this.preText = preText;
		this.nextText = nextText;
		this.exitText = exitText;
		this.doneText = doneText;
		this.createTime = createTime;
		this.pages = pages;
		this.userId = userId;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	@Override
	public String toString() {
		return "Survey [surveyId=" + surveyId + ", title=" + title + ", preText=" + preText + ", nextText=" + nextText
				+ ", exitText=" + exitText + ", doneText=" + doneText + ", createTime=" + createTime + ", pages="
				+ pages + ", userId=" + userId + ", closed=" + closed + ", logoPath=" + logoPath + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (closed ? 1231 : 1237);
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((doneText == null) ? 0 : doneText.hashCode());
		result = prime * result + ((exitText == null) ? 0 : exitText.hashCode());
		result = prime * result + ((logoPath == null) ? 0 : logoPath.hashCode());
		result = prime * result + ((nextText == null) ? 0 : nextText.hashCode());
		result = prime * result + ((pages == null) ? 0 : pages.hashCode());
		result = prime * result + ((preText == null) ? 0 : preText.hashCode());
		result = prime * result + ((surveyId == null) ? 0 : surveyId.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		Survey other = (Survey) obj;
		if (closed != other.closed)
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (doneText == null) {
			if (other.doneText != null)
				return false;
		} else if (!doneText.equals(other.doneText))
			return false;
		if (exitText == null) {
			if (other.exitText != null)
				return false;
		} else if (!exitText.equals(other.exitText))
			return false;
		if (logoPath == null) {
			if (other.logoPath != null)
				return false;
		} else if (!logoPath.equals(other.logoPath))
			return false;
		if (nextText == null) {
			if (other.nextText != null)
				return false;
		} else if (!nextText.equals(other.nextText))
			return false;
		if (pages == null) {
			if (other.pages != null)
				return false;
		} else if (!pages.equals(other.pages))
			return false;
		if (preText == null) {
			if (other.preText != null)
				return false;
		} else if (!preText.equals(other.preText))
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
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
		
			
}
