package survey.dao;


import org.springframework.stereotype.Repository;

import survey.sercurity.Permission;

@Repository("permissionDao")
public interface PermissionDao extends BaseDao<Permission>{

	public Permission getMaxPermissionPositionAndNum();
	public String isExistTheUrl(String url);
	public void deleteLinksByPermissionId(Integer id);
	public void swichStatus(Permission model);
	public int getMaxPosition();
	
}
