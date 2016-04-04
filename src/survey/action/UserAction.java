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

	private String confirmPassword;//用于确认密码是否一致

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * 
	 * @return 调转至注册页面
	 */
	public String toRegister() {

		return "toRegisterPage";
	}

	/**
	 * 进行注册，如果 用户名不为空 切 数据库中不存在，密码一致 则进行添加用户
	 * 密码：加密
	 * 注册成功：跳转至登录页面
	 * 注册失败：调转至注册页面
	 * @return 
	 */
	public String register() {

		if (model.getName().trim().equals("")) {
			addFieldError("name", "用命名不能为空");
		}
		if (userService.getUserByName(model.getName().trim()) != null) {
			addFieldError("name", "用户已存在");
		}
		 if (!model.getPassword().equals(confirmPassword)) {
		 addFieldError("password", "密码不一致");
		 }
		if (model.getNikeName().trim().equals("")) {
			addFieldError("nikeName", "昵称不能为空");
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
	 * 登录：校验用户名 和 密码
	 * 登录成功：添加至session 属性中，进入页面
	 * 登录失败：调转至登录页面
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
		addActionError("用户名 或 密码不正确！");
		return "login_failed";
	}

	
	private Map<String, Object> session;
	@Override
	public void setSession(Map<String, Object> arg0) {

		session = arg0;
	}

}
