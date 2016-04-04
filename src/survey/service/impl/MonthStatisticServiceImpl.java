package survey.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import survey.dao.MonthExcelDao;
import survey.dao.SurveyDao;
import survey.dao.UserDao;
import survey.model.MonthExcel;
import survey.model.Survey;
import survey.quartz.MyAuthentication;
import survey.service.ImformationCollectionService;
import survey.service.MonthStatisticService;

@Service("monthStatisticService")
public class MonthStatisticServiceImpl implements MonthStatisticService{

	@Autowired
	private SurveyDao surveyDao;
	@Autowired
	private ImformationCollectionService infCollectionService;
	@Autowired
	private MonthExcelDao monthExcelcDao;
	@Autowired
	private UserDao userDao;
	
	/**
	 * 得到 相隔数月的survey
	 */
	public List<Survey> getMonthsSurvey(){
		
		List<Survey> allSurveys = surveyDao.getAll();
		List<Survey> monthSurveys = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
		Date date = new Date();
		int days = 0;
		
		for (Survey survey : allSurveys) {
			days = (int)(date.getTime() - survey.getCreateTime().getTime())/1000/60/60/24;
			System.out.println(days);
			if (days % 30 == 0) {
				monthSurveys.add(survey);
			}
		}
		
		return monthSurveys;
	}
	
	/**
	 * 把对应的survey生成excel 并保存在服务器端，并把路径插入数据库
	 */
	public void insertWorkBookOfSurveys(List<Survey> monthSurveys,ServletContext servletContext){
		MonthExcel monthExcel = null;
		for (Survey survey : monthSurveys) {
			HSSFWorkbook workbook = infCollectionService.getWorkBook(survey);
			String dir = servletContext.getRealPath("/excels");
			long l = System.nanoTime();
			File newFile = new File(dir, l + ".xls");
			
			
			try {
				workbook.write(new FileOutputStream(newFile));
			}  catch (Exception e) {
				e.printStackTrace();
			}
			
			dir =  l + ".xls";
			SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
			String fileDate = format.format(new Date());
			monthExcel = new MonthExcel(survey.getSurveyId(), dir, fileDate);
			monthExcelcDao.add(monthExcel);
		}
	}

	/**
	 * 发送emails
	 */
	@Override
	public void sendEmails(List<Survey> monthSurveys,ServletContext servletContext) {

		int surveyId = 0;
		for (Survey survey : monthSurveys) {
			surveyId = survey.getSurveyId();
			String name =  userDao.getNameBySurveyId(surveyId);
			sendEmail(name,servletContext,surveyId,survey.getTitle());
		}
		
	}
	
	/**
	 * 发送email
	 * @param toEmail
	 * @param servletContext
	 * @param surveyId
	 * @param title
	 */
	public void sendEmail(String toEmail,ServletContext servletContext,Integer surveyId,String title){
		
		try {
			
			String url = monthExcelcDao.getUrl(surveyId).get(0);
			String dir = servletContext.getRealPath("/excels");
			System.out.println(dir + url);
			DataSource source = new FileDataSource(new File(dir + "//" + url));
			
			String fromEmail = "15340527662@163.com";
			String host = "smtp.163.com";
			Properties properties = System.getProperties();
			properties.setProperty("mail.smtp.host", host);
			properties.put("mail.smtp.auth", "true");
			
			MyAuthentication myauth = new MyAuthentication(fromEmail, "mm13452035366");
			Session session = Session.getDefaultInstance(properties);
			
			
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject("累月数据");
			
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("累月数据");
			
			
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(title + "近月的数据");
			multipart.addBodyPart(messageBodyPart);
			
			message.setContent(multipart);
			
			  String username = "15340527662@163.com";
		      String password = "mm13452035366";
		      message.saveChanges(); 
		      Transport transport = session.getTransport("smtp");
		      transport.connect("smtp.163.com", username, password);
		      transport.sendMessage(message, message.getAllRecipients());
		      transport.close();
		      
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
