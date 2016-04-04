package survey.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.ParameterAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import survey.sercurity.Permission;
import survey.sercurity.Role;
import survey.service.RoleService;

@Scope("prototype")
@Controller
public class RoleAction extends BaseAction<Role> implements ParameterAware{

	@Autowired
	private RoleService roleService;
	
	private List<Permission> otherPermissions;//����ɫû�е�Ȩ��
	public void setOtherPermissions(List<Permission> otherPermissions) {
		this.otherPermissions = otherPermissions;
	}
	
	public List<Permission> getOtherPermissions() {
		return otherPermissions;
	}
	
	private List<Role> roles ;//ȫ���Ľ�ɫ
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	
	/**
	 * ���������޸Ľ�ɫҳ��
	 * @return
	 */
	public String toAddOrUpdateRole(){
		
		if (model.getRoleId() != null) {
			model =  roleService.getRoleById(model.getRoleId());
		}
		otherPermissions = roleService.getOtherPermissions(model.getRoleId());
		
		return "addOrUpdateRolePage";
	}
	
	/**
	 * ���������޸Ľ�ɫ
	 * @return
	 */
	public String addOrUpdateRole(){
		
		if (model.getRoleId() != null) { //�޸�
			roleService.updateRole(model,params);
		}else {//����
	
			roleService.addRoleAndLinks(model,params);
		}
		return "listRoleAction";
	}

	private Map<String, String[]> params ;
	@Override
	public void setParameters(Map<String, String[]> arg0) {

		params = arg0;
	}
	
	/**
	 *��ɫ�б�ҳ��
	 */
	public String toListRolesPage(){
		
		roles = roleService.getAll();
		return "listRolesPage";
	}
	
	
	/**
	 * ɾ����ɫ
	 * @return
	 */
	public String deleteRole(){
		
		roleService.deleteRolecAndLink(model.getRoleId());
		return "listRoleAction";
	}
	
	
}
