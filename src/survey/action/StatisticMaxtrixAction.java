package survey.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import survey.model.MaxtrixStatistic;
import survey.model.Question;
import survey.model.Statistic;
import survey.service.QuestionService;
import survey.service.StatisticService;

@Scope("prototype")
@Controller
public class StatisticMaxtrixAction extends BaseAction<Question> {

	@Autowired
	private StatisticService statisticService;
	private Statistic statistic;
	@Autowired
	private QuestionService questionService;

	public Statistic getStatistic() {
		return statistic;
	}

	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}

	public String execute() {
		statistic = statisticService.getStatistic(model);
		model = questionService.getQuestionWithOptions(model.getQuestionId()) ;
		return SUCCESS;
	}

	
	/**
	 * 获取百分比
	 * @param maxtrixRowId
	 * @param maxtrixColId
	 * @param maxtrixOptionId
	 * @return
	 */
	public String getPercent(Integer maxtrixRowId, Integer maxtrixColId, Integer maxtrixOptionId) {
		for (MaxtrixStatistic maxtrixStatistic : statistic.getMaxtrixStatistic()) {
			if (maxtrixRowId == maxtrixStatistic.getMaxtrixRowId() && maxtrixColId == maxtrixStatistic.getMaxtrixColId()
					&& maxtrixOptionId == maxtrixStatistic.getMaxtrixOptionId()) {
				return maxtrixStatistic.getCount() + "人次  所占比例 ：" + maxtrixStatistic.getPercent();
			}
		}

		return "===";
	}
}
