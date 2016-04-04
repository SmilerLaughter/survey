package survey.dao;

import java.util.List;

import survey.model.MonthExcel;

public interface MonthExcelDao extends BaseDao<MonthExcel>{

	List<String> getUrl(Integer surveyId);
	
}
