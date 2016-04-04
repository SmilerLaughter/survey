package survey.sercurity;

public class Permission {

	private Integer permissionId;
	private String permissionName = "Î´ÃüÃû";
	private String permissionDesc = "ÎÞ";
	private String url;
	private int position;
	private int permissionNum;
	private boolean open;
	
	

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionDesc() {
		return permissionDesc;
	}

	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPermissionNum() {
		return permissionNum;
	}

	public void setPermissionNum(int permissionNum) {
		this.permissionNum = permissionNum;
	}

	@Override
	public String toString() {
		return "Permission [permissionId=" + permissionId + ", permissionName=" + permissionName + ", permissionDesc="
				+ permissionDesc + ", url=" + url + ", position=" + position + ", permissionNum=" + permissionNum
				+ ", open=" + open + "]";
	}

	public Permission(String permissionName, String permissionDesc, String url, int position, int permissionNum) {
		super();
		this.permissionName = permissionName;
		this.permissionDesc = permissionDesc;
		this.url = url;
		this.position = position;
		this.permissionNum = permissionNum;
	}
	
	public Permission() {
		// TODO Auto-generated constructor stub
	}
}
