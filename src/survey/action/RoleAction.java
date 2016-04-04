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
	
	private List<Permission> otherPermissions;//本角色没有的权限
	public void setOtherPermissions(List<Permission> otherPermissions) {
		this.otherPermissions = otherPermissions;
	}
	
	public List<Permission> getOtherPermissions() {
		return otherPermissions;
	}
	
	private List<Role> roles ;//全部的角色
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	
	/**
	 * 新增或者修改角色页面
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
	 * 新增或者修改角色
	 * @return
	 */
	public String addOrUpdateRole(){
		
		if (model.getRoleId() != null) { //修改
			roleService.updateRole(model,params);
		}else {//增加
	
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
	 *角色列表页面
	 */
	public String toListRolesPage(){
		
		roles = roleService.getAll();
		return "listRolesPage";
	}
	
	
	/**
	 * 删除角色
	 * @return
	 */
	public String deleteRole(){
		
		roleService.deleteRolecAndLink(model.getRoleId());
		return "listRoleAction";
	}
	
	
}
