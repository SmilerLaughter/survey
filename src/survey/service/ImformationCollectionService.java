package survey.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import survey.model.Survey;

public interface ImformationCollectionService {

	HSSFWorkbook getWorkBook(Survey model);

}
