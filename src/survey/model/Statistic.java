package survey.model;

import java.util.List;

public class Statistic {

	private long count;
	private String title;
	private List<OptionStatistic> optionStatistics;
	private List<MaxtrixStatistic> maxtrixStatistic;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<OptionStatistic> getOptionStatistics() {
		return optionStatistics;
	}

	public void setOptionStatistics(List<OptionStatistic> optionStatistics) {
		this.optionStatistics = optionStatistics;
	}

	public List<MaxtrixStatistic> getMaxtrixStatistic() {
		return maxtrixStatistic;
	}

	public void setMaxtrixStatistic(List<MaxtrixStatistic> maxtrixStatistic) {
		this.maxtrixStatistic = maxtrixStatistic;
	}

	@Override
	public String toString() {
		return "Statistic [count=" + count + ", optionStatistics=" + optionStatistics + ", maxtrixStatistic="
				+ maxtrixStatistic + "]";
	}

	public Statistic() {
		// TODO Auto-generated constructor stub
	}

	public Statistic(long count, List<OptionStatistic> optionStatistics, List<MaxtrixStatistic> maxtrixStatistic) {
		super();
		this.count = count;
		this.optionStatistics = optionStatistics;
		this.maxtrixStatistic = maxtrixStatistic;
	}
	
	

}
