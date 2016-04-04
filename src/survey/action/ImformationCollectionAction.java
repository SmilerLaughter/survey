package survey.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import survey.model.Survey;
import survey.service.ImformationCollectionService;

@Scope("prototype")
@Controller
public class ImformationCollectionAction extends BaseAction<Survey>{

	private InputStream inputStream;
	
	@Autowired
	private  ImformationCollectionService imformationCollectionService ;
	
	public String execute(){
		inputStream = getInputStream();
		return SUCCESS;
	}
	
	/**
	 * ·µ»Øinputstream
	 * @return
	 */
	public InputStream getInputStream(){
		
		HSSFWorkbook workbook = imformationCollectionService.getWorkBook(model);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			workbook.write(baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ByteArrayInputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
		return inputStream;
	}
	
}
