package survey.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import survey.dao.RoleDao;
import survey.dao.UserDao;
import survey.model.User;
import survey.sercurity.Role;
import survey.service.AnthorizeService;

@Service("anthorizeService")
public class AnthorizeServiceImpl implements AnthorizeService{

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserDao userDao;
	
	/**
	 * ��ȡ�û� �����ɫ
	 */
	@Override
	public User getUserWithRoles(Integer userId) {
		User user = userDao.getById(userId);
		List<Role> roles = roleDao.getRolesByUserId(userId);
		user.setRoles(roles);
		
		return user;
	}

	/**
	 * �õ�ָ���û�û�еĽ�ɫ��
	 */
	@Override
	public List<Role> getOtherRolesByUserId(Integer userId) {
		
		return roleDao.getOtherRolesByUserId(userId);
	}

	/**
	 * �����û��Ľ�ɫ����ɾ�����еģ��ٽ������
	 */
	@Override
	public void updateRoleOfUser(Integer userId, String[] roleIds) {

		roleDao.deleteLinksByUserId(userId);
		List<Map<String, Integer>> list = getLinks(userId, roleIds);
		if (list != null) {
			
			roleDao.addRoleUsers(list);
		}
	}

	/**
	 * �õ�һ�� user - role�����ӱ�� ��¼���� 
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	public List<Map<String, Integer>> getLinks(Integer userId, String[] roleIds){
		
		List<Map<String, Integer>> list = new ArrayList<>();
		Map<String, Integer> map = null;
		if (list != null) {
			
			for (String roleId : roleIds) {
				map = new HashMap<>();
				map.put("roleId", Integer.parseInt(roleId));
				map.put("userId", userId);
				list.add(map);
				
			}
			return list;
		}
		
		return null;
	}

	@Override
	public void clearAuthorizationsByUserId(Integer userId) {

		roleDao.deleteLinksByUserId(userId);
	}
}
