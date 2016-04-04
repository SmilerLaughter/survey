package survey.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import survey.dao.BaseDao;
import survey.dao.PermissionDao;
import survey.sercurity.Permission;
import survey.service.PermissionService;

@Service("permissionService")
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;

	@Autowired
	@Override
	public void setBaseDao(BaseDao<Permission> baseDao) {
		// TODO Auto-generated method stub
		baseDao = permissionDao;
	}

	/**
	 * 增加permission 处理权限位与权限值，权限值用二进制处理
	 */

	@Override
	public boolean addPermission(Permission model) {

		if (permissionDao.isExistTheUrl(model.getUrl()) != null) {
			return false;
		} else {

			Permission maxPermission = permissionDao.getMaxPermissionPositionAndNum();
			int position = 0;
			int num = 0;
			if ( maxPermission != null  ) { // 若一条记录也没有 ，则position = 0，num = 1
				if (maxPermission.getPermissionNum()  >= (1 << 29 )) { // 如果num已经超过最大值，则position + 1
					num = 1;
					position = maxPermission.getPosition() + 1;
				} else { // 若没有达到最大值，则在当前板块 num 佐移 1
					position = maxPermission.getPosition();
					num =maxPermission.getPermissionNum() << 1;
				}
			} else {
				num = 1;
			}

			model.setPosition(position);
			model.setPermissionNum(num);
			add(model);

			return true;
		}
	}

	


	public Permission getPermissionById(Integer id){
		return getById(id);
	}
	
	/**
	 * 更新权限的操作，不能更新权限位 和 权限值 URL要判断数据库中是否已经存在
	 */
	@Override
	public boolean updatePermission(Permission model) {
		if (permissionDao.isExistTheUrl(model.getUrl()) != null ){
			return false;
		} else {
			update(model);
			return true;
		}
	}

	/**
	 * 删除权限，以及角色-权限对应表中的权限
	 * 
	 * @param id
	 */
	public void deletePermission(Integer id) {
		permissionDao.deleteLinksByPermissionId(id);
		delete(id);
	}

	
	/**
	 * 获取所有的权限
	 */
	@Override
	public List<Permission> getAllPermissions() {
		
		return getAll();
	}

	@Override
	public void swichUpdate(Permission model) {

		permissionDao.swichStatus(model);
	}
}
