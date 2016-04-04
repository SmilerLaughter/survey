package survey.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import survey.dao.QuestionDao;
import survey.dao.StatisticDao;
import survey.model.MaxtriOption;
import survey.model.MaxtrixCol;
import survey.model.MaxtrixRow;
import survey.model.MaxtrixStatistic;
import survey.model.Option;
import survey.model.OptionStatistic;
import survey.model.Question;
import survey.model.Statistic;
import survey.service.QuestionService;
import survey.service.StatisticService;
import survey.service.SurveyService;

@Service("statisticService")
public class StatisticServiceImpl implements StatisticService {

	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private StatisticDao statisticDao;

	/**
	 * ��ȡͳ�ƶ���
	 */
	@Override
	public Statistic getStatistic(Question model) {

		Statistic statistic = new Statistic();
		Question question = questionDao.getById(model.getQuestionId());
		statistic.setTitle(question.getTitle());

		questionService.setOptionsOfQuestion(question);
		long count = 0;
		if (question.getQuestionTypeId() < 6) {// �Ǿ���
			count = statisticDao.getPersonCountOfAnswerOption(model.getQuestionId());
			statistic.setOptionStatistics(getStatisticOptions(question));

		} else if (question.getQuestionTypeId() > 6) {// ����
			count = statisticDao.getPersonCountOfAnswerMaxtrix(model.getQuestionId());
			if (count != 0) {
				statistic.setMaxtrixStatistic(getMaxtrixStatistics(question, count));
			}
		}
		statistic.setCount(count);

		return statistic;

	}

	/**
	 * ��ȡ���������ͳ�ƽ�����ϣ����ø�����Ԫ��İٷֱ�
	 * @param question ����
	 * @param count ����
	 * @return ����ͳ�Ƽ���
	 */
	public List<MaxtrixStatistic> getMaxtrixStatistics(Question question, long count) {
		MaxtrixStatistic maxtrixStatistic = null;
		List<MaxtrixStatistic> maxtrixStatistics = new ArrayList<>();
		if (question.getMaxtrixCols() != null && question.getMaxtrixRows() != null) {
			for (MaxtrixRow maxtrixRow : question.getMaxtrixRows()) {
				for (MaxtrixCol maxtrixCol : question.getMaxtrixCols()) {
					
					if (question.getMaxtrixSelectOptions() != null && question.getMaxtrixSelectOptions().size() > 0) {
						for (MaxtriOption maxtriOption : question.getMaxtrixSelectOptions()) {//����ʽ��������
							maxtrixStatistic = new MaxtrixStatistic(maxtrixRow.getRowId(), maxtrixCol.getColId(), maxtriOption.getOptionId());
							maxtrixStatistic.setCount(statisticDao.getMaxtriCount(maxtrixStatistic));
							maxtrixStatistic.setPercent((float) maxtrixStatistic.getCount() / count * 100);
							maxtrixStatistics.add(maxtrixStatistic);
						}
					} else { // �����ľ������� 
						maxtrixStatistic = new MaxtrixStatistic(maxtrixRow.getRowId(), maxtrixCol.getColId(), null);
						maxtrixStatistic.setCount(statisticDao.getMaxtriCount(maxtrixStatistic));
						maxtrixStatistic.setPercent((float) maxtrixStatistic.getCount() / count * 100);
						maxtrixStatistics.add(maxtrixStatistic);
					}
				}
				
			}
		}
		return maxtrixStatistics;
	}

	/**
	 * ��ȡѡ��Ϊ���ͳ�ƽ��
	 * @param question
	 * @return
	 */
	public List<OptionStatistic> getStatisticOptions(Question question) {

		List<OptionStatistic> optionStatistics = new ArrayList<>();
		if (question.getOptions() != null) {
			for (Option option : question.getOptions()) {
				optionStatistics.add(statisticDao.getOptionStatic(option.getOptionId()));
			}
		}
		if (question.getOtherType() != 0) { //����������
			optionStatistics.add(new OptionStatistic(0, "����", statisticDao.getCountOfOther(question.getQuestionId())));
		}

		return optionStatistics;
	}

}
