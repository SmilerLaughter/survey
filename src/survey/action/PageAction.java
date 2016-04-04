package survey.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import survey.model.Page;
import survey.service.PageService;

@Scope("prototype")
@Controller
public class PageAction extends BaseAction<Page> {

	@Autowired
	private PageService pageSurvice ;
	
	/**
	 * 调转到 新增 或者 更新 page 的页面，是更新操作，需要进行回显
	 */
	public String toAddOrUpdatePage(){
		if(model.getSurveyId() == null){//surveyId 为空时，更新操作

			model = pageSurvice.getById(model.getPageId()); //更新model的值，为了进行回显
		}
		return "addPage"; //调转到 page 表单
	}
	
	/**
	 * add or update page
	 * 如果pageId 不为空 ，就是更新操作 -- 更新需要传id，而新增不用。
	 * 如果为空，则是新增操作
	 */
	public String addOrUpdatePage(){
		if(model.getPageId() != null){
			pageSurvice.update(model);
		}
		else {
			pageSurvice.add(model);
		}
		return "designAction";
	}
	
	/**
	 * 删除对应的page ，以及此page 包含的questions ，以及question中的对应的所有选项
	 */
	public  String toDeletePage(){
		pageSurvice.deletePageAndQuestions(model.getPageId());
		return "designAction";
	}
	
}
