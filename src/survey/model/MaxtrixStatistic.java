package survey.model;

public class MaxtrixStatistic {

	private Integer maxtrixRowId;
	private Integer maxtrixColId;
	private Integer maxtrixOptionId;

	private long count;
	private float percent;

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

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	@Override
	public String toString() {
		return "MaxtrixStatistic [maxtrixRowId=" + maxtrixRowId + ", maxtrixColId=" + maxtrixColId
				+ ", maxtrixOptionId=" + maxtrixOptionId + ", count=" + count + ", percent=" + percent + "]";
	}

	public MaxtrixStatistic(Integer maxtrixRowId, Integer maxtrixColId, Integer maxtrixOptionId) {
		super();
		this.maxtrixRowId = maxtrixRowId;
		this.maxtrixColId = maxtrixColId;
		this.maxtrixOptionId = maxtrixOptionId;
		
	}

	public MaxtrixStatistic() {
		// TODO Auto-generated constructor stub
	}

}
