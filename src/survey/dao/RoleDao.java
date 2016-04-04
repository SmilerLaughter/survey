package survey.dao;

import java.util.List;
import java.util.Map;

import survey.sercurity.Permission;
import survey.sercurity.Role;

public interface RoleDao extends BaseDao<Role>{

	List<Permission> getPermissionsByRoleId(Integer roleId);

	List<Permission> getOtherPermissions(Integer roleId);

	void addRolePermissions(List<Map<String, Integer>> map);
	
	void deleteLinksByRoleId(Integer id);

	List<Role> getRolesByUserId(Integer userId);
	
	List<Role> getOtherRolesByUserId(Integer userId);

	void deleteLinksByUserId(Integer userId);

	void addRoleUsers(List<Map<String, Integer>> map);
}
