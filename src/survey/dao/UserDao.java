package survey.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import survey.model.User;

@Repository
@Qualifier("userDao")
public interface UserDao extends BaseDao<User>{

	public User  getUserByName(String name);
	public User getUserToLogin(User user);
	public String getNameBySurveyId(int surveyId);
}
