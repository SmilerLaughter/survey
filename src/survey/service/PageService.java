package survey.service;

import survey.model.Page;

public interface PageService extends BaseService<Page> {

	void deletePageAndQuestions(Integer pageId);

	void move(Integer pageId, Integer targetPageId,  int pose);

	void deepCopy(Integer pageId, Integer targetPageId, Integer targetSurveyId, int pose);

	
}
