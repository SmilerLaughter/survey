package survey.service;

import java.util.List;
import java.util.Map;

import survey.sercurity.Permission;
import survey.sercurity.Role;

public interface RoleService extends BaseService<Role>{

	Role getRoleById(Integer roleId);

	List<Permission> getOtherPermissions(Integer roleId);

	void addRoleAndLinks(Role model, Map<String, String[]> params);

	void updateRole(Role model, Map<String, String[]> params);

	void deleteRolecAndLink(Integer roleId);

}
