package model;

public class MemberLicense implements LicenseHolder{
	private int id;
	private String account;
	private String license;

	public MemberLicense() {
			
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MemberLicense(String account,String license) {
		this.account = account;
		this.license = license;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
}
