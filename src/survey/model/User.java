package survey.model;

import java.util.Arrays;
import java.util.List;


import survey.sercurity.Permission;
import survey.sercurity.Role;

public class User {

	private Integer  userId;
	private String name;
	private  String password;
	private String nikeName;
	private List<Role> roles;
	private long[] permissionSum;
	private boolean superAdmin;
	
	
	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public void setPermissionSum(long[] permissionSum) {
		this.permissionSum = permissionSum;
	}
	
	public long[] getPermissionSum() {
		return permissionSum;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer id) {
		this.userId = id;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", password=" + password + ", nikeName=" + nikeName
				+ ", roles=" + roles + ", permissionSum=" + Arrays.toString(permissionSum) + ", superAdmin="
				+ superAdmin + "]";
	}

	public User( String name, String password, String nikeName) {
		super();

		this.name = name;
		this.password = password;
		this.nikeName = nikeName;
	}
	
	public boolean hasPermission(Permission permission){
		int position = permission.getPosition();
		long number = permission.getPermissionNum();
		
		return !((permissionSum[position] & number) == 0);
	}
	
}
