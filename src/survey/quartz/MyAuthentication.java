package survey.quartz;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthentication extends Authenticator {

	private String user;
	private String password;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MyAuthentication(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}

	protected  PasswordAuthentication getPasswordAuthentication(){
		
		return new PasswordAuthentication(user, password);
	}
}
