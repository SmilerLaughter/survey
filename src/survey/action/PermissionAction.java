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

	private List<Permission> permissions;//ȫ����Ȩ��

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * ���������޸�Ȩ��ҳ��
	 * @return
	 */
	public String toAddOrUpdatePermissionPage() {

		if (model.getPermissionId() != null) {
			model = permissionService.getPermissionById(model.getPermissionId());
		}
		return "addPermissionPage";
	}

	/**
	 * �������߸���Ȩ��
	 * @return 
	 */
	public String addOrUpdatePermission() {

		if (model.getPermissionId() != null) {
			if (!permissionService.updatePermission(model)) {
				addFieldError("url", "��URL�Ѵ���");
				return INPUT;
			}

		} else {
			if (!permissionService.addPermission(model)) {
				addFieldError("url", "��URL�Ѵ���");
				return INPUT;
			}

		}
		return "toListPermissionAction";
	}

	/**
	 * Ȩ���б�ҳ��
	 * @return
	 */
	public String toListPermissionsPage() {
		permissions = permissionService.getAllPermissions();
		return "listPermissionsPage";
	}
	
	/**
	 * ɾ��Ȩ��
	 * @return
	 */
	public String deletePermission(){
		
		permissionService.deletePermission(model.getPermissionId());
		return "toListPermissionAction";
	}
	
	/**
	 * �л�״̬--Ȩ���Ƿ�������˿���
	 * @return
	 */
	public String swichStatus() {
		
		System.out.println(model);
		permissionService.swichUpdate(model);
		return "toListPermissionAction";
	}
}
