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
	 * ��ת�� ���� ���� ���� page ��ҳ�棬�Ǹ��²�������Ҫ���л���
	 */
	public String toAddOrUpdatePage(){
		if(model.getSurveyId() == null){//surveyId Ϊ��ʱ�����²���

			model = pageSurvice.getById(model.getPageId()); //����model��ֵ��Ϊ�˽��л���
		}
		return "addPage"; //��ת�� page ��
	}
	
	/**
	 * add or update page
	 * ���pageId ��Ϊ�� �����Ǹ��²��� -- ������Ҫ��id�����������á�
	 * ���Ϊ�գ�������������
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
	 * ɾ����Ӧ��page ���Լ���page ������questions ���Լ�question�еĶ�Ӧ������ѡ��
	 */
	public  String toDeletePage(){
		pageSurvice.deletePageAndQuestions(model.getPageId());
		return "designAction";
	}
	
}
