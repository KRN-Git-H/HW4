package dao;

import java.util.List;

import model.MemberLicense;

public interface MemberLicenseDao {
	//create
	void create(MemberLicense memberLicense);
	
	//read
	MemberLicense readLicense(String license);
	List<MemberLicense> readAllList();
	List<MemberLicense> readAllAccountList();
	List<MemberLicense>	readAccountList(String account);
	List<MemberLicense> readLicenseList(String license);	
	
	//update
	void update(MemberLicense memberLicense);
	
	//delete
	void delete(MemberLicense memberLicense);

	
	
	
}
