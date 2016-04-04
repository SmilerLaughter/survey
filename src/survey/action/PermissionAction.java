package survey.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import survey.sercurity.Permission;
import survey.service.PermissionService;

@Scope("prototype")
@Controller("permissionAction")
public class PermissionAction extends BaseAction<Permission> {
	@Autowired
	private PermissionService permissionService;

	private List<Permission> permissions;//全部的权限

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * 新增或者修改权限页面
	 * @return
	 */
	public String toAddOrUpdatePermissionPage() {

		if (model.getPermissionId() != null) {
			model = permissionService.getPermissionById(model.getPermissionId());
		}
		return "addPermissionPage";
	}

	/**
	 * 新增或者更新权限
	 * @return 
	 */
	public String addOrUpdatePermission() {

		if (model.getPermissionId() != null) {
			if (!permissionService.updatePermission(model)) {
				addFieldError("url", "该URL已存在");
				return INPUT;
			}

		} else {
			if (!permissionService.addPermission(model)) {
				addFieldError("url", "该URL已存在");
				return INPUT;
			}

		}
		return "toListPermissionAction";
	}

	/**
	 * 权限列表页面
	 * @return
	 */
	public String toListPermissionsPage() {
		permissions = permissionService.getAllPermissions();
		return "listPermissionsPage";
	}
	
	/**
	 * 删除权限
	 * @return
	 */
	public String deletePermission(){
		
		permissionService.deletePermission(model.getPermissionId());
		return "toListPermissionAction";
	}
	
	/**
	 * 切换状态--权限是否对所有人开放
	 * @return
	 */
	public String swichStatus() {
		
		System.out.println(model);
		permissionService.swichUpdate(model);
		return "toListPermissionAction";
	}
}
