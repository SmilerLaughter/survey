package survey.dao;

import java.util.List;

import survey.model.AnswerMaxtrix;
import survey.model.AnswerOption;
import survey.model.AnswerText;
import survey.model.MaxtrixStatistic;
import survey.model.OptionStatistic;

public interface StatisticDao {

	public long getPersonCountOfAnswerOption(Integer questionId);
	public long getPersonCountOfAnswerMaxtrix(Integer questionId);
	public OptionStatistic getOptionStatic(Integer optionId);
	public long getCountOfOther(Integer questionId);
	public long getMaxtriCount(MaxtrixStatistic maxtrixStatistic);
	public String[] getUuIds(Integer surveyId);
	public List<AnswerOption> getOptionAnswers(String uuId);
	public List<AnswerMaxtrix> getMaxtrixAnswers(String uuId);
	public List<AnswerText> getTextAnswers(String uuId);
}
