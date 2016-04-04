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
	 * ����permission ����Ȩ��λ��Ȩ��ֵ��Ȩ��ֵ�ö����ƴ���
	 */

	@Override
	public boolean addPermission(Permission model) {

		if (permissionDao.isExistTheUrl(model.getUrl()) != null) {
			return false;
		} else {

			Permission maxPermission = permissionDao.getMaxPermissionPositionAndNum();
			int position = 0;
			int num = 0;
			if ( maxPermission != null  ) { // ��һ����¼Ҳû�� ����position = 0��num = 1
				if (maxPermission.getPermissionNum()  >= (1 << 29 )) { // ���num�Ѿ��������ֵ����position + 1
					num = 1;
					position = maxPermission.getPosition() + 1;
				} else { // ��û�дﵽ���ֵ�����ڵ�ǰ��� num ���� 1
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
	 * ����Ȩ�޵Ĳ��������ܸ���Ȩ��λ �� Ȩ��ֵ URLҪ�ж����ݿ����Ƿ��Ѿ�����
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
	 * ɾ��Ȩ�ޣ��Լ���ɫ-Ȩ�޶�Ӧ���е�Ȩ��
	 * 
	 * @param id
	 */
	public void deletePermission(Integer id) {
		permissionDao.deleteLinksByPermissionId(id);
		delete(id);
	}

	
	/**
	 * ��ȡ���е�Ȩ��
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
