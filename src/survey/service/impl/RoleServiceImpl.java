package survey.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import survey.dao.BaseDao;
import survey.dao.RoleDao;
import survey.sercurity.Permission;
import survey.sercurity.Role;
import survey.service.RoleService;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

	@Autowired
	private RoleDao roleDao ;
	
	@Override
	public void setBaseDao(BaseDao<Role> roleDao) {
		baseDao = roleDao;
		
	}

	/**
	 * 通过id 获取role
	 */
	@Override
	public Role getRoleById(Integer roleId) {

		Role role = getById(roleId);
		List<Permission> permissions = roleDao.getPermissionsByRoleId(roleId);
		role.setPermissions(permissions);
		return role;
	}

	/**
	 * 获取指定角色没有拥有的权限集合
	 */
	@Override
	public List<Permission> getOtherPermissions(Integer roleId) {

		List<Permission> permissions = roleDao.getOtherPermissions(roleId);
		return permissions;
	}

	
	@Override
	public void addRoleAndLinks(Role model, Map<String, String[]> params) {

		add(model);
		List<Map<String, Integer> > linkparams = getLinkParams(model, params);
		if (linkparams != null) {
			
			roleDao.addRolePermissions(linkparams);
		}
		
	}
	
	public List<Map<String, Integer>> getLinkParams(Role model,Map<String, String[]> params ){
		
		int roleId = model.getRoleId();
		String[] permissionIds = params.get("permissionId");
		if (permissionIds != null) {
			
			int permissionId = 0;
			List<Map<String, Integer>>  links = new ArrayList<>();
			Map<String, Integer> paramMap = null;
			if (permissionIds != null && permissionIds.length > 0) {
				for (String permissionIdStr : permissionIds) {
					paramMap = new HashMap<String, Integer>();
					permissionId = Integer.parseInt(permissionIdStr);
					paramMap.put("permissionId", permissionId);
					paramMap.put("roleId", model.getRoleId());
					links.add(paramMap);
				}
			}
			return links;
		}
		return null;
	}

	/**
	 * 先删除连接表对应的role，然后再进行添加
	 */
	@Override
	public void updateRole(Role model,Map<String, String[]> params) {
			update(model);
			roleDao.deleteLinksByRoleId(model.getRoleId());
			List<Map<String, Integer> > linkparams = getLinkParams(model, params);
			if (linkparams != null) {
				
				roleDao.addRolePermissions(linkparams);
			}
	}

	@Override
	public void deleteRolecAndLink(Integer roleId) {

		roleDao.deleteLinksByRoleId(roleId);
		delete(roleId);
	}

}
