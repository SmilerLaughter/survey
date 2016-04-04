package survey.action;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import survey.model.Survey;
import survey.service.SurveyService;

@Scope("prototype")
@Controller
public class UploadAction extends BaseAction<Survey> implements ServletContextAware {

	private File image;
	private String imageContentType;
	private String imageFileName;
	
	@Autowired
	private SurveyService surveyService;

		/**
		 * 以下为图片上传做准备
		 * @return
		 */
		
	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	
	/**
	 * 跳转到目标页面
	 * @return
	 */
	public String toAddImage(){
		
		return "addLogoPage";
	}
	
	
	/**
	 * 重新设置上传图片的路径，并插入数据库
	 * @return
	 */
	public String addImage(){
		String dir = servletContext.getRealPath("/upload");
		if(image != null){
		String ext = getImageFileName().substring(getImageFileName().indexOf("."));
		long l = System.nanoTime();
		File newFile = new File(dir,l+ext);
		getImage().renameTo(newFile);
		dir = "/upload/" + l + ext;
		model.setLogoPath(dir);
		surveyService.updateLogoPhotoPath(model);
		}
		return "designAction";
		
	}

	private ServletContext servletContext ;
	
	@Override
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub
		servletContext = arg0;
	}
}
