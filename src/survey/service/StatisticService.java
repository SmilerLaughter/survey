package survey.service;

import survey.model.Question;
import survey.model.Statistic;

public interface StatisticService {

	public Statistic getStatistic(Question model);
}
