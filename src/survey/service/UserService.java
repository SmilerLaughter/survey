package survey.service;

import org.springframework.stereotype.Service;

import survey.model.User;

@Service
public interface UserService extends BaseService<User> {
	
	public User getUserByName(String name);
	public User getUserToLogin(User user);
	public void setUserPermissionSum(User user);
}
