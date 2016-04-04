package survey.action;

import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import survey.model.OptionStatistic;
import survey.model.Question;
import survey.model.Statistic;
import survey.service.StatisticService;

@Scope("prototype")
@Controller
public class StatisticAction extends BaseAction<Question> {

	
	private int chartType;
	
	@Autowired
	private StatisticService statisticService;
	
	private Statistic statistic;
	public Statistic getStatistic() {
		return statistic;
	}
	
	private JFreeChart chart;
	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}


	public void setChartType(int chartType) {
		this.chartType = chartType;
	}

	public int getChartType() {
		return chartType;
	}
	
	public String execute() {
		System.out.println(model.getQuestionId());
		this.chart = getChart();
		return SUCCESS;
	}
	
	
	/**
	 * 根据问题统计 和 所选的图形类型 返回对应的图表
	 * @return
	 */
	public JFreeChart getChart(){
		
		statistic = statisticService.getStatistic(model);
		DefaultPieDataset pieDatasets = null;
		DefaultCategoryDataset cateds = null;
		
		Font font  = new Font("宋体", 0, 20);
		
		if (chartType < 3) {
			pieDatasets = new DefaultPieDataset();
			for (OptionStatistic optionStatistic : statistic.getOptionStatistics()) {
				pieDatasets.setValue(optionStatistic.getContent(), optionStatistic.getOptionCount());
			}
			
		}else {
			cateds = new DefaultCategoryDataset();
			for (OptionStatistic optionStatistic : statistic.getOptionStatistics()) {
				cateds.addValue(optionStatistic.getOptionCount(), optionStatistic.getContent(), " ");
			}
		}
		String title = statistic.getTitle() + "\n共有" + statistic.getCount()+ "人次作答";
		switch (chartType) {
		case 1:
			chart = ChartFactory.createPieChart(title, pieDatasets, true, false, false); 
			break;
		case 2:
			chart = ChartFactory.createPieChart3D(title, pieDatasets, true, true, true);
			chart.getPlot().setForegroundAlpha(0.75f);
			break;
		case 3:
			chart = ChartFactory.createBarChart(title, "选项", "人数",cateds,PlotOrientation.HORIZONTAL, true, false, false);
			break;
		case 4 :
			chart = ChartFactory.createBarChart(title, "选项", "人数", cateds, PlotOrientation.VERTICAL, true, false, false);
			break;
		case 5 :
			chart = ChartFactory.createBarChart3D(title, "选项", "人数", cateds, PlotOrientation.HORIZONTAL, true,true, true);
			break;
		case 6 :
			chart = ChartFactory.createBarChart3D(title, "选项", "人数", cateds, PlotOrientation.VERTICAL, true,true, true);
			break;
		case 7 :
			chart = ChartFactory.createLineChart(title, "选项", "人数", cateds,  PlotOrientation.VERTICAL, true, true, false);
			break;
		case 8:
			chart = ChartFactory.createLineChart3D(title, "选项", "人数", cateds,  PlotOrientation.VERTICAL, true, true, true);
			break;
		}	
		
		chart.getTitle().setFont(font);
		chart.getLegend().setItemFont(font);
		
		if (chart.getPlot() instanceof PiePlot) {
			PiePlot piePlot = (PiePlot) chart.getPlot();
			piePlot.setLabelFont(font);
			piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{1}/{3}={2}"));
		}else {
			chart.getCategoryPlot().getRangeAxis().setLabelFont(font);
			chart.getCategoryPlot().getRangeAxis().setTickLabelFont(font);
			chart.getCategoryPlot().getDomainAxis().setLabelFont(font);
			chart.getCategoryPlot().getDomainAxis().setTickLabelFont( font);
		}
		
		return chart;
	}
}
