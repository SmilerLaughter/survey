package survey.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.ParameterAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import sun.net.www.protocol.http.AuthCache;
import survey.model.User;
import survey.sercurity.Role;
import survey.service.AnthorizeService;
import survey.service.UserService;
@Scope("prototype")
@Controller("authorizeAction")
public class AuthorizeAction extends BaseAction<User> implements ParameterAware{

	@Autowired
	private UserService userService;
	@Autowired
	private AnthorizeService anthorizeService;
	
	private List<Role> otherRoles;//��δӵ�е�Ȩ��
	
	public void setOtherRoles(List<Role> otherRoles) {
		this.otherRoles = otherRoles;
	}
	
	public List<Role> getOtherRoles() {
		return otherRoles;
	}
	
	private List<User> users ;//���е��û�
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	/**
	 * ��ת����Ȩҳ��
	 * @return
	 */
	public String toAuthorizePage(){
 		users = userService.getAll();
		return "authorizePage";
	}
	
	/**
	 * �����û���ɫҳ��
	 * @return
	 */
	public String toUpdateAuthorization(){
		
		model = anthorizeService.getUserWithRoles(model.getUserId());
		otherRoles = anthorizeService.getOtherRolesByUserId(model.getUserId());
		return "updateAuthorizationPage";
	}
	
	/**
	 * �����û���ɫ
	 * @return
	 */
	public String updateAuthorization(){
		String[] roleIds = params.get("roleId");
		anthorizeService.updateRoleOfUser(model.getUserId(),roleIds);
		
		return "authorizeAction";
	}

	/**
	 * ����û�ӵ�еĽ�ɫ
	 * @return
	 */
	public String clearAuthorization(){
		
		anthorizeService.clearAuthorizationsByUserId(model.getUserId());
		return "authorizeAction";
	}
	
	private Map<String, String[]> params;
	@Override
	public void setParameters(Map<String, String[]> arg0) {
		// TODO Auto-generated method stub
		params =arg0;
	}
	

	

	
	
}
