package survey.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import survey.interceptor.UserAware;
import survey.model.User;
import survey.service.UserService;
import survey.util.MD5;

@Scope("prototype")
@Controller
public class UserAction extends BaseAction<User> implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;

	private String confirmPassword;//����ȷ�������Ƿ�һ��

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * 
	 * @return ��ת��ע��ҳ��
	 */
	public String toRegister() {

		return "toRegisterPage";
	}

	/**
	 * ����ע�ᣬ��� �û�����Ϊ�� �� ���ݿ��в����ڣ�����һ�� ���������û�
	 * ���룺����
	 * ע��ɹ�����ת����¼ҳ��
	 * ע��ʧ�ܣ���ת��ע��ҳ��
	 * @return 
	 */
	public String register() {

		if (model.getName().trim().equals("")) {
			addFieldError("name", "����������Ϊ��");
		}
		if (userService.getUserByName(model.getName().trim()) != null) {
			addFieldError("name", "�û��Ѵ���");
		}
		 if (!model.getPassword().equals(confirmPassword)) {
		 addFieldError("password", "���벻һ��");
		 }
		if (model.getNikeName().trim().equals("")) {
			addFieldError("nikeName", "�ǳƲ���Ϊ��");
		}
		if (!hasErrors()) {
			try {
				model.setPassword(MD5.getMd5(model.getPassword()));
				model.setName(model.getName().trim());
				userService.add(model);
				return "reg_success";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return INPUT;
	}

	/**
	 * ��¼��У���û��� �� ����
	 * ��¼�ɹ��������session �����У�����ҳ��
	 * ��¼ʧ�ܣ���ת����¼ҳ��
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {

		model.setPassword(MD5.getMd5(model.getPassword()));
		model.setName(model.getName().trim());
		User user = userService.getUserToLogin(model);
		if (user != null) {
			
			userService.setUserPermissionSum(user);
			session.put("user", user);
			return SUCCESS;
		}
		addActionError("�û��� �� ���벻��ȷ��");
		return "login_failed";
	}

	
	private Map<String, Object> session;
	@Override
	public void setSession(Map<String, Object> arg0) {

		session = arg0;
	}

}
