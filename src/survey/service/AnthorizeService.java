package survey.service;

import java.util.List;

import survey.model.User;
import survey.sercurity.Role;

public interface AnthorizeService {

	User getUserWithRoles(Integer userId);

	List<Role> getOtherRolesByUserId(Integer userId);

	void updateRoleOfUser(Integer userId, String[] roleIds);

	void clearAuthorizationsByUserId(Integer userId);

}
