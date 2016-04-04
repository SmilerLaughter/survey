package survey.service;

import java.util.List;

import survey.sercurity.Permission;

public interface PermissionService {

	boolean addPermission(Permission model);

	boolean updatePermission(Permission model);

	Permission getPermissionById(Integer permissionId);

	List<Permission> getAllPermissions();

	void deletePermission(Integer id);

	void swichUpdate(Permission model);
}
