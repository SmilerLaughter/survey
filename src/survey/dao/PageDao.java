package survey.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import survey.model.Page;

@Repository
@Qualifier("pageDao")
public interface PageDao extends BaseDao<Page>{

	List<Page> getPagesBySurveyId(int id);

	void updatePageOrder(Page page);

	List<Page> getPrePages(Page target);

	List<Page> getNextPages(Page target);

	List<Page> getNextPagesByOrderNoAndPageId(Page page);

	List<Page> getPrePagesByOrderNoAndPageId(Page page);


}
