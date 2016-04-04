package survey.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import survey.dao.BaseDao;
import survey.dao.PermissionDao;
import survey.dao.RoleDao;
import survey.dao.UserDao;
import survey.model.User;
import survey.sercurity.Permission;
import survey.sercurity.Role;
import survey.service.UserService;

@Service
@Qualifier("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private PermissionDao permissionDao;

	/*
	 * 覆盖父类中的值，不然调用基本方法时会调用父类方法，即baseDao接口中的方法，而并没有对应的mapper对象
	 */
	@Autowired
	public void setBaseDao(BaseDao<User> userDao) {
		baseDao = userDao;
	}

	@Override
	public User getUserByName(String name) {
		return userDao.getUserByName(name);
	}

	@Override
	public User getUserToLogin(User user) {

		return userDao.getUserToLogin(user);
	}

	/**
	 * 先设置权限数组的大小
	 */
	@Override
	public void setUserPermissionSum(User user) {

		List<Role> roles = roleDao.getRolesByUserId(user.getUserId());
		int arraylen = permissionDao.getMaxPosition();
		long[] permissionSum = new long[arraylen + 1];
		user.setPermissionSum(permissionSum);
		int position = 0;
		int number = 0;
		List<Permission> permissions = null;
		if (roles != null) {
			for (Role role : roles) {
				if (role.isSuperAdmin()) {
					user.setSuperAdmin(true);
				}
				 permissions =  roleDao.getPermissionsByRoleId(role.getRoleId());
				 if (permissions != null) {
					 for (Permission permission : permissions) {
						 position = permission.getPosition();
						 number = permission.getPermissionNum();
						permissionSum[position] = permissionSum[position] | number;
						System.out.println(permissionSum[position]);
					}
				 }
			}
		}
		System.out.println(user);
		
		
	}
	
	

}